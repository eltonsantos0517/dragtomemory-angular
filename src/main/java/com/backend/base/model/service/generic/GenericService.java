package com.backend.base.model.service.generic;

import java.util.Date;
import java.util.List;

import com.backend.base.model.dao.generic.GenericDAO;
import com.backend.base.model.entity.generic.IEntity;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.Filter;
import com.googlecode.objectify.Key;

public abstract class GenericService<T extends IEntity> {

	private Class<T> clazz;

	protected GenericService(Class<T> clazz, String nameSpace) {
		NamespaceManager.set(nameSpace);
		this.clazz = clazz;
	}

	public Key<T> save(T entity) {
		Date date = new Date();
		entity.setUpdatedAt(date);
		if (entity.getObjectId() == null) {
			entity.setCreatedAt(date);
		}

		return getDAO().save(entity);
	}

	public void delete(T entity) {
		getDAO().delete(entity);
	}

	public void deleteById(long objectId) {
		Key<T> key = Key.create(clazz, objectId);
		getDAO().delete(key);
	}
	
	public T get(Key<T> key){
		return getDAO().get(key);
	}

	public T get(Long id) {
		try {
			return getDAO().get(id);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public T getByColumn(String columnName, Object value) {
		try {
			return getDAO().getByColumn(columnName, value);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public T getByFilter(Filter filter) {
		try {
			return getDAO().getByFilter(filter);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public T getByFilterAndColumn(Filter filter, String columnName, Object value) {
		try {
			return getDAO().getByFilterAndColumn(filter, columnName, value);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<T> listByColumn(String columnName, Object value) {
		try {
			return getDAO().listByColumn(columnName, value);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<T> listAll() {
		try {
			return getDAO().listAll();
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<T> listByFilter(final Filter filter){
		try {
			return getDAO().listByFilter(filter);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public CollectionResponse<T> listPage(int limit, String cursor, String order, Filter filter) throws EntityNotFoundException {
		try {
			return getDAO().listPage(limit, cursor, order, filter);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	

	public CollectionResponse<T> listPage(int limit, String cursor, String order) throws EntityNotFoundException {
		try {
			return getDAO().listPage(limit, cursor, order);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<T> listPage(int limit, int offset) {
		try {
			return getDAO().listPage(limit, offset);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<T> listByColumnWithLimitAndOrder(String columnName, Object value, int limit, String order) {
		try {
			return getDAO().listByColumnWithLimitAndOrder(columnName, value, limit, order);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<T> listByColumnWithOrder(String columnName, Object value, String order) {
		try {
			return getDAO().listByColumnWithOrder(columnName, value, order);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<T> listByFilterAndColumn(Filter filter, String columnName, Object value) {
		try {
			return getDAO().listByFilterAndColumn(filter, columnName, value);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public long count() {
		return getDAO().count();
	}
	
	public long countWithFilter(Filter filter) {
		return getDAO().countWithFilter(filter);
	}

	public abstract GenericDAO<T> getDAO();

}
