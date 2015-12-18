package com.backend.base.model.service;

import java.security.NoSuchAlgorithmException;

import com.backend.base.controller.to.AccountTO;
import com.backend.base.model.dao.AccountDAO;
import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.service.generic.GenericService;
import com.backend.base.security.util.SecurityUtil;
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

	@Override
	public GenericDAO<AccountEntity> getDAO() {
		return accountDAO;
	}

}
