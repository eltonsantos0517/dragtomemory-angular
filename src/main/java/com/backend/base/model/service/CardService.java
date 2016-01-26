package com.backend.base.model.service;

import org.joda.time.DateTime;

import com.backend.base.model.dao.CardDAO;
import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.CardEntity;
import com.backend.base.model.service.generic.GenericService;
import com.googlecode.objectify.Key;

public class CardService extends GenericService<CardEntity>{
	
	private CardDAO cardDAO;
	
	public CardService() {
		super(CardEntity.class, null);
		cardDAO = new CardDAO();
	}

	@Override
	public GenericDAO<CardEntity> getDAO() {
		return cardDAO;
	}
	
	public Key<CardEntity> saveCard(final CardEntity entity){
		entity.setStage("1");
		entity.setNextRevision(new DateTime().plusDays(1).toDate());
		return save(entity);
	}

	public Key<CardEntity> editCard(final CardEntity entity) {
		if(entity.isChangeStage()){
			entity.setStage("1");
			entity.setNextRevision(new DateTime().plusDays(1).toDate());
		}
		
		return save(entity);
	}

}
