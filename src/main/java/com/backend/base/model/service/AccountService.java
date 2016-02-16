package com.backend.base.model.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.security.auth.login.AccountException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.backend.base.controller.to.AccountTO;
import com.backend.base.exception.InvalidEmailException;
import com.backend.base.exception.InvalidTokenException;
import com.backend.base.exception.MismatchedPasswordsException;
import com.backend.base.model.dao.AccountDAO;
import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.entity.RecoveryTokenEntity;
import com.backend.base.model.service.generic.GenericService;
import com.backend.base.security.service.UserDetailsService;
import com.backend.base.security.util.SecurityUtil;
import com.backend.base.util.EmailUtil;
import com.backend.base.util.Util;
import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;

public class AccountService extends GenericService<AccountEntity> {

	private AccountDAO accountDAO;

	public AccountService() {
		super(AccountEntity.class, null);
		accountDAO = new AccountDAO();
	}

	public Long saveAccount(final AccountTO to)
			throws NoSuchAlgorithmException, InvalidEmailException, MismatchedPasswordsException, AccountException {

		if (to.getObjectId() == null) {

			AccountEntity entity = getByColumn("email", to.getEmail());

			if (entity != null) {
				// é via Facebook?
				if (to.getFacebookToken() != null) {
					// Facebook
					entity.setFacebookToken(to.getFacebookToken());
					return super.save(entity).getId();
				} else {
					throw new AccountException("An account already exists with the email");
				}
			} else {
				entity = new AccountEntity();
				entity.setFirstName(to.getFirstName());
				entity.setLastName(to.getLastName());
				entity.setEmail(to.getEmail());
				if (SecurityUtil.validateNewPassword(to.getPassword(), to.getPasswordAgain())
						|| to.getFacebookToken() != null) {
					entity.setPassword(SecurityUtil.encryptPassword(to.getPassword()));
				} else {
					// TODO
					throw new MismatchedPasswordsException("Invalid password");
				}

				return super.save(entity).getId();
			}
		} else {
			// Update
			AccountEntity entity = getByColumn("email", to.getEmail());
			entity.setFirstName(to.getFirstName());
			entity.setLastName(to.getLastName());

			if (to.getProfileImage() != null && !to.getProfileImage().isEmpty()) {
				try {
					entity.setProfileImage(new Blob(to.getProfileImage().getBytes("UTF-8")));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			return super.save(entity).getId();
			//
			// if (SecurityUtil.validateNewPassword(to.getPassword(),
			// to.getPasswordAgain())) {
			// entity.setPassword(SecurityUtil.encryptPassword(to.getPassword()));
			// to.setPassword(null);
			// to.setPasswordAgain(null);
			// return super.save(entity).getId();
			// } else {
			// throw new MismatchedPasswordsException("Invalid password");
			// }
		}
	}

	public void forgotPassword(final String email) throws Exception {

		if (!Util.isValidEmail(email)) {
			throw new InvalidEmailException("Invalid e-mail");
		}

		final UserDetailsService service = new UserDetailsService();
		AccountTO account = null;

		try {
			account = service.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			System.out.println("User not found");
			return;
		}

		RecoveryTokenService recoveryTokenService = new RecoveryTokenService();
		recoveryTokenService.inactivateTokensByUser(account);

		final RecoveryTokenEntity recoveryToken = new RecoveryTokenEntity();
		recoveryToken.setToken(SecurityUtil.newToken().replace("-", ""));
		recoveryToken.setUserId(account.getObjectId());
		recoveryToken.setValidate(SecurityUtil.getValidateOfTokenInDays());
		recoveryToken.setCreatedAt(new Date());
		recoveryToken.setActive(true);
		recoveryTokenService.save(recoveryToken);

		final String http = "<html><head></head><body><p><a href=\""
				+ "https://dragtomemory.appspot.com/recovery-password/" + recoveryToken.getToken()
				+ "\">Clique aqui</a></p></body></html>";
		System.out.println(http);
		try {
			EmailUtil.sendEmail(email, account.getUsername(), "Change Password Request", http);
		} catch (UnsupportedEncodingException | MessagingException e) {
			System.out.println("Error sending email");
		}
	}

	public void recoveryPassword(final RecoveryTokenEntity recoveryToken) throws Exception {

		final RecoveryTokenService service = new RecoveryTokenService();
		final RecoveryTokenEntity recoveryTokenPersisted = service.getByColumn("token", recoveryToken.getToken());

		if (recoveryTokenPersisted == null) {
			throw new InvalidTokenException("Invalid token");
		}

		if (!recoveryTokenPersisted.isActive()
				|| recoveryTokenPersisted.getCreatedAt().compareTo(new Date()) > recoveryTokenPersisted.getValidate()) {
			throw new InvalidTokenException("Expired token");
		}

		if (!SecurityUtil.validateNewPassword(recoveryToken.getNewPassword(), recoveryToken.getNewPasswordAgain())) {
			throw new MismatchedPasswordsException("Mismatched passwords");
		}

		Key<AccountEntity> accountKey = Key.create(AccountEntity.class, recoveryTokenPersisted.getUserId());
		AccountEntity user = super.get(accountKey.getId());

		if (user == null) {
			throw new Exception("Invalid user in token");
		}

		user.setPassword(SecurityUtil.encryptPassword(recoveryToken.getNewPassword()));
		save(user);

		// inativar token
		recoveryTokenPersisted.setActive(false);
		service.save(recoveryTokenPersisted);
	}

	@Override
	public GenericDAO<AccountEntity> getDAO() {
		return accountDAO;
	}
}
