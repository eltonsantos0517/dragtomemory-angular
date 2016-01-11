package com.backend.base.model.service;

import java.util.List;

import com.backend.base.model.dao.RecoveryTokenDAO;
import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.RecoveryTokenEntity;
import com.backend.base.model.service.generic.GenericService;
import com.backend.base.security.entity.User;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class RecoveryTokenService extends GenericService<RecoveryTokenEntity> {

	private RecoveryTokenDAO recoveryTokenDAO;

	protected RecoveryTokenService() {
		super(RecoveryTokenEntity.class, null);
		recoveryTokenDAO = new RecoveryTokenDAO();
	}

	@Override
	public GenericDAO<RecoveryTokenEntity> getDAO() {
		return recoveryTokenDAO;
	}

	public void inactivateTokensByUser(final User user) {
		//TODO possivel gargalo, com o tempo muitos tokens inativo vão ficar na base de dados, e do jeito q foi implementado ira trazer todos eles e percorre-los
		
		Filter f = new FilterPredicate("active", FilterOperator.EQUAL, true);
		final List<RecoveryTokenEntity> rtes = super.listByFilterAndColumn(f, "userId", user.getObjectId());

		if (rtes != null && rtes.size() > 0) {
			for (RecoveryTokenEntity rte : rtes) {
				rte.setActive(false);
				super.save(rte);
			}
		}else{
			System.out.println("Nenhum token para invalidar");
		}
	}

}
