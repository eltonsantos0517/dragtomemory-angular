package com.backend.base.model.dao.generic;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.backend.base.model.entity.generic.GenericEntity;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

/**
 * @author brunogc
 *
 * @param <T>
 */
public class GenericDAO<T extends GenericEntity> {
	private Class<T> clazz;

	protected GenericDAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Key<T> save(T entity) {
		return ofy().save().entity(entity).now();
	}

	public void delete(T entity) {
		ofy().delete().entity(entity).now();
	}

	public void delete(Key<T> key) {
		ofy().delete().key(key).now();
	}

	public T get(Long id) throws EntityNotFoundException {
		return ofy().load().type(clazz).id(id).now();
	}

	public T getByColumn(String columnName, Object value) throws EntityNotFoundException {
		return ofy().load().type(clazz).filter(columnName, value).first().now();
	}

	public T getByFilter(Filter filter) throws EntityNotFoundException {
		return ofy().load().type(clazz).filter(filter).first().now();
	}

	public T getByFilterAndColumn(Filter filter, String columnName, Object value) throws EntityNotFoundException {
		return ofy().load().type(clazz).filter(filter).filter(columnName, value).first().now();
	}

	public List<T> listAll() throws EntityNotFoundException {
		return ofy().load().type(clazz).list();
	}
	
	public CollectionResponse<T> listPage(int limit, String cursor) throws EntityNotFoundException {
		Query<T> query = ofy().load().type(clazz).limit(limit);

		if (cursor != null && !cursor.isEmpty()) {
			query = query.startAt(Cursor.fromWebSafeString(cursor));
		}
		boolean continu = false;
		QueryResultIterator<T> iterator = query.iterator();
		List<T> resultList = new ArrayList<>();
		while (iterator.hasNext()) {
			T t = iterator.next();
			resultList.add(t);
			continu = true;
		}

		if (continu) {
			Cursor cursorRet = iterator.getCursor();
			CollectionResponse<T> response = CollectionResponse.<T> builder().setItems(resultList)
					.setNextPageToken(cursorRet.toWebSafeString()).build();
			return response;
		}
		return null;
	}
	
	public List<T> listPage(int limit, int offset) throws EntityNotFoundException {
		return ofy().load().type(clazz).orderKey(true).limit(limit).offset(offset).list();
	}
	
	public List<T> listByColumn(String columnName, Object value) throws EntityNotFoundException {
		return ofy().load().type(clazz).filter(columnName, value).list();
	}

	public List<T> listByColumnWithLimitAndOrder(String columnName, Object value, int limit, String order) throws EntityNotFoundException {
		return ofy().load().type(clazz).limit(limit).filter(columnName, value).order(order).list();
	}
	
	public List<T> listByColumnWithOrder(String columnName, Object value, String order) throws EntityNotFoundException {
		return ofy().load().type(clazz).filter(columnName, value).order(order).list();
	}
	
	public List<T> listByFilterAndColumn(Filter filter, String columnName, Object value) throws EntityNotFoundException {
		return ofy().load().type(clazz).filter(filter).filter(columnName, value).list();
	}
}