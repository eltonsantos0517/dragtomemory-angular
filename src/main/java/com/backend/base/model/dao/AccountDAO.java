package com.backend.base.model.dao;

import java.util.logging.Logger;

import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.AccountEntity;
import com.googlecode.objectify.ObjectifyService;

public class AccountDAO extends GenericDAO<AccountEntity> {

	private static final Logger LOG = Logger.getLogger(AccountDAO.class.getName());

	static {
		LOG.info("Registrando entity " + AccountEntity.class.getSimpleName());
		ObjectifyService.register(AccountEntity.class);
	}

	public AccountDAO() {
		super(AccountEntity.class);
	}
}