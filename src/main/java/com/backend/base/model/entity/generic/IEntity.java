package com.backend.base.model.entity.generic;

import java.util.Date;

public interface IEntity<T> {
	
	T getObjectId();
	void setObjectId(T objectId);
	public Date getCreatedAt();
	public void setCreatedAt(Date createdAt);
	public Date getUpdatedAt();
	public void setUpdatedAt(Date updatedAt);

}
