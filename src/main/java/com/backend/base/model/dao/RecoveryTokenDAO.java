package com.backend.base.model.dao;

import java.util.logging.Logger;

import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.RecoveryTokenEntity;
import com.googlecode.objectify.ObjectifyService;

public class RecoveryTokenDAO extends GenericDAO<RecoveryTokenEntity> {

	private static final Logger LOG = Logger.getLogger(AccountDAO.class.getName());

	static {
		LOG.info("Registrando entity " + RecoveryTokenEntity.class.getSimpleName());
		ObjectifyService.register(RecoveryTokenEntity.class);
	}

	public RecoveryTokenDAO() {
		super(RecoveryTokenEntity.class);
	}
	
	

}
