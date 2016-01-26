package com.backend.base.model.dao;

import java.util.logging.Logger;

import org.joda.time.DateTime;

import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.CardEntity;
import com.googlecode.objectify.ObjectifyService;

public class CardDAO extends GenericDAO<CardEntity> {

	private static final Logger LOG = Logger.getLogger(AccountDAO.class.getName());

	static {
		LOG.info("Registrando entity " + CardEntity.class.getSimpleName());
		ObjectifyService.register(CardEntity.class);
	}

	public CardDAO() {
		super(CardEntity.class);
	}

}
