package com.backend.base.model.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.mail.MessagingException;

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
import com.backend.base.security.entity.User;
import com.backend.base.security.service.UserDetailsService;
import com.backend.base.security.util.SecurityUtil;
import com.backend.base.util.EmailUtil;
import com.backend.base.util.Util;
import com.googlecode.objectify.Key;

public class AccountService extends GenericService<AccountEntity> {

	private AccountDAO accountDAO;

	public AccountService() {
		super(AccountEntity.class, null);
		accountDAO = new AccountDAO();
	}

	public Key<AccountEntity> createAccount(AccountTO to) throws NoSuchAlgorithmException {
		AccountEntity entity = new AccountEntity();
		entity.setFirstName(to.getFirstName());
		entity.setLastName(to.getLastName());
		entity.setEmail(to.getEmail());
		entity.setPassword(SecurityUtil.encryptPassword(to.getPassword()));

		return super.save(entity);
	}

	public Key<AccountEntity> changeAccount(AccountTO to) throws NoSuchAlgorithmException {

		Key<AccountEntity> accountKey = Key.create(AccountEntity.class, to.getObjectId());
		AccountEntity entity = super.get(accountKey.getId());

		entity.setFirstName(to.getFirstName());
		entity.setLastName(to.getLastName());

		if (to.getPassword() != null && !to.getPassword().isEmpty()) {
			entity.setPassword(SecurityUtil.encryptPassword(to.getPassword()));

			to.setPassword(null);
			to.setPasswordAgain(null);
		}

		return super.save(entity);
	}

	public void forgotPassword(final String email) throws Exception {

		if (!Util.isValidEmail(email)) {
			throw new InvalidEmailException("Invalid e-mail");
		}

		final UserDetailsService service = new UserDetailsService();
		User user = null;

		try {
			user = service.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			System.out.println("User not found");
			return;
		}

		RecoveryTokenService recoveryTokenService = new RecoveryTokenService();
		recoveryTokenService.inactivateTokensByUser(user);

		final RecoveryTokenEntity recoveryToken = new RecoveryTokenEntity();
		recoveryToken.setToken(SecurityUtil.newToken().replace("-", ""));
		recoveryToken.setUserId(user.getObjectId());
		recoveryToken.setValidate(SecurityUtil.getValidateOfTokenInDays());
		recoveryToken.setCreatedAt(new Date());
		recoveryToken.setActive(true);
		recoveryTokenService.save(recoveryToken);

		final String http = "<html><head></head><body><p><a href=\"" + "https://gae-spring-angular.appspot.com/recovery-password/" +recoveryToken.getToken()+ "\">Clique aqui</a></p></body></html>";
		System.out.println(http);
		try {
			EmailUtil.sendEmail(email, user.getUsername(), "Change Password Request", http);
		} catch (UnsupportedEncodingException | MessagingException e) {
			System.out.println("Error sending email");
		}
	}

	public void recoveryPassword(final RecoveryTokenEntity recoveryToken) throws Exception {
		
		final RecoveryTokenService service = new RecoveryTokenService();
		final RecoveryTokenEntity recoveryTokenPersisted = service.getByColumn("token", recoveryToken.getToken());
		
		if(recoveryTokenPersisted == null){
			throw new InvalidTokenException("Invalid token");
		}
		
		if(!recoveryTokenPersisted.isActive() || recoveryTokenPersisted.getCreatedAt().compareTo(new Date()) > recoveryTokenPersisted.getValidate()){
			throw new InvalidTokenException("Expired token");
		}
		
		if(!SecurityUtil.validateNewPassword(recoveryToken.getNewPassword(), recoveryToken.getNewPasswordAgain())){
			throw new MismatchedPasswordsException("Mismatched passwords");
		}
		
		Key<AccountEntity> accountKey = Key.create(AccountEntity.class, recoveryTokenPersisted.getUserId());
		AccountEntity user = super.get(accountKey.getId());
		
		if(user==null){
			throw new Exception("Invalid user in token");
		}
		
		AccountTO to = new AccountTO();
		
		to.setCreatedAt(user.getCreatedAt());
		to.setEmail(user.getEmail());
		to.setFirstName(user.getFirstName());
		to.setLastName(user.getLastName());
		to.setObjectId(user.getObjectId());
		to.setPassword(recoveryToken.getNewPassword());
		to.setUpdatedAt(user.getUpdatedAt());
		
		changeAccount(to);
		
		//inativar token
		recoveryTokenPersisted.setActive(false);
		service.save(recoveryTokenPersisted);
	}

	@Override
	public GenericDAO<AccountEntity> getDAO() {
		return accountDAO;
	}


}
