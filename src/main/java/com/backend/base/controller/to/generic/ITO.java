package com.backend.base.controller.to.generic;

import java.util.Date;

public interface ITO<T> {
	
	T getObjectId();
	void setObjectId(T objectId);
	public Date getCreatedAt();
	public void setCreatedAt(Date createdAt);
	public Date getUpdatedAt();
	public void setUpdatedAt(Date updatedAt);

}
