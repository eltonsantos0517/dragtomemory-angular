package com.backend.base.model.service;

import org.joda.time.DateTime;

import com.backend.base.model.dao.CardDAO;
import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.CardEntity;
import com.backend.base.model.service.generic.GenericService;
import com.backend.base.util.Util;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.googlecode.objectify.Key;

public class CardService extends GenericService<CardEntity> {

	private final static String FILTER_CARD = "today-cards";

	private CardDAO cardDAO;

	public CardService() {
		super(CardEntity.class, null);
		cardDAO = new CardDAO();
	}

	@Override
	public GenericDAO<CardEntity> getDAO() {
		return cardDAO;
	}

	public CardEntity saveCard(final CardEntity entity) {
		entity.setStage(1);
		entity.setNextRevision(new DateTime().plusDays(1).toDate());
		save(entity);
		return entity;
	}

	public CardEntity editCard(final CardEntity entity) {
		if (entity.isChangeStage()) {
			entity.setStage(1);
			entity.setNextRevision(new DateTime().plusDays(1).toDate());
		}
		save(entity);
		return entity;
	}

	public CardEntity done(final CardEntity entity) throws Exception {

		entity.setStage(entity.getStage() + 1);
		entity.setNextRevision(Util.getDateOfNextRevision(entity.getStage()));
		super.save(entity);
		return entity;
	}

	public CollectionResponse<CardEntity> listCards(int limit, String cursor, String order, String filter)
			throws EntityNotFoundException {
		if (FILTER_CARD.equalsIgnoreCase(filter)) {
			Filter f = new FilterPredicate("nextRevision", FilterOperator.LESS_THAN_OR_EQUAL,
					new DateTime().plusDays(1).toDate());
			return listPage(limit, cursor, order, f);
		} else {
			return listPage(limit, cursor, order);
		}
	}
}
