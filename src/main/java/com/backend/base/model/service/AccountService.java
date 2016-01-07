package com.backend.base.model.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.backend.base.controller.to.AccountTO;
import com.backend.base.exception.InvalidEmailException;
import com.backend.base.model.dao.AccountDAO;
import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.AccountEntity;
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
		to.setPassword(null);
		to.setPasswordAgain(null);

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
	
	public void forgotPassword(final String email) throws Exception{
		
		if(!Util.isValidEmail(email)){
			throw new InvalidEmailException("Invalid e-mail");
		}
		
		final UserDetailsService service = new UserDetailsService();
		User entity = null;
		
		try{
			entity = service.loadUserByUsername(email);
		}catch(UsernameNotFoundException e){
			System.out.println("User not found");
			return;
		}
		
		final String http = "<html><head></head><body><p> Your new password is 'XxXx'</p></body></html>";
		
		try {
			EmailUtil.sendEmail(email, entity.getUsername(), "Change Password Request", http);
		} catch (UnsupportedEncodingException | MessagingException e) {
			System.out.println("Error sending email");
		}
	}

	@Override
	public GenericDAO<AccountEntity> getDAO() {
		return accountDAO;
	}

}
