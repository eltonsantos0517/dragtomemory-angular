package com.backend.base.model.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContextHolder;

import com.backend.base.model.dao.CardDAO;
import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.entity.CardEntity;
import com.backend.base.model.service.generic.GenericService;
import com.backend.base.security.entity.UserAuthentication;
import com.backend.base.util.EmailUtil;
import com.backend.base.util.Util;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class CardService extends GenericService<CardEntity> {

	private final static String FILTER_CARD = "today-cards";

	private CardDAO cardDAO;

	public CardService() {
		super(CardEntity.class, null);
		cardDAO = new CardDAO();
	}

	private long getCurrentUserId() {
		final UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication.getDetails().getObjectId();
	}

	@Override
	public GenericDAO<CardEntity> getDAO() {
		return cardDAO;
	}

	public CardEntity saveCard(final CardEntity entity) {
		entity.setStage(1);
		entity.setNextRevision(new DateTime().plusDays(1).toDate());
		entity.setOwnerId(getCurrentUserId());
		save(entity);
		return entity;
	}

	public CardEntity editCard(final CardEntity entity) {
		if (entity.isChangeStage()) {
			entity.setStage(1);
			entity.setNextRevision(new DateTime().plusDays(1).toDate());
		}
		entity.setOwnerId(getCurrentUserId());
		save(entity);
		return entity;
	}

	public CardEntity done(final CardEntity entity) throws Exception {

		entity.setStage(entity.getStage() + 1);
		entity.setNextRevision(Util.getDateOfNextRevision(entity.getStage()));
		super.save(entity);
		return entity;
	}

	public long count(String filter) {
		if (FILTER_CARD.equalsIgnoreCase(filter)) {
			return countWithFilter(getFilter(getCurrentUserId()));
		} else {
			return countWithFilter(getOwnerFilter());
		}
	}

	public CollectionResponse<CardEntity> listCards(int limit, String cursor, String order, String filter)
			throws EntityNotFoundException {

		if (FILTER_CARD.equalsIgnoreCase(filter)) {
			return listPage(limit, cursor, order, getFilter(getCurrentUserId()));
		} else {
			return listPage(limit, cursor, order, getOwnerFilter());
		}
	}

	public void processExpiretedCards() {

		final List<CardEntity> expiretedCards = listExpiretedCards();

		for (CardEntity cardEntity : expiretedCards) {
			cardEntity.setNextRevision(new DateTime().plusDays(2).toDate());
			cardEntity.setStage(1);
			save(cardEntity);
		}
	}
	
	public void warnUsersAboutCardsToday(){
		AccountService accountService = new AccountService();
		
		List<AccountEntity> users = accountService.listAll();
		long qtdCardsByUser = 0;
		
		for (AccountEntity user : users) {
			qtdCardsByUser = countWithFilter(getFilter(user.getObjectId()));
			if(qtdCardsByUser > 0){
				String hello = "Hello" + (user.getFirstName() != null ? " "+user.getFirstName() : "");
				String msg = "<html><head></head><body><h3>"+hello+",</h3><p>You have "+qtdCardsByUser+" memos to revision today, go to <a href=\""
						+ "https://dragtomemory.appspot.com\">Drag to memory</a>!</p></body></html>";
				try {
					EmailUtil.sendEmail(user.getEmail(), user.getFirstName(), "Change Password Request", msg);
				} catch (UnsupportedEncodingException | MessagingException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	private List<CardEntity> listExpiretedCards() {
		return listByFilter(CompositeFilterOperator.and(
				new FilterPredicate("nextRevision", FilterOperator.LESS_THAN_OR_EQUAL, new DateTime().minusDays(1).toDate()),
				new FilterPredicate("stage", FilterOperator.IN, Arrays.asList(1, 2, 3, 4))));
	}

	private Filter getFilter(long userId) {
		return CompositeFilterOperator.and(
				new FilterPredicate("nextRevision", FilterOperator.LESS_THAN_OR_EQUAL, new DateTime().toDate()),
				new FilterPredicate("stage", FilterOperator.IN, Arrays.asList(1, 2, 3, 4)),
				new FilterPredicate("ownerId", FilterOperator.EQUAL, userId)
			);
	}
	
	private Filter getOwnerFilter(){
		return new FilterPredicate("ownerId", FilterOperator.EQUAL, getCurrentUserId());
	}
}
