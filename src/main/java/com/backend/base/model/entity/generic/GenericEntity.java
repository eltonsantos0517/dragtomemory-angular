package com.backend.base.model.entity.generic;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public abstract class GenericEntity implements Serializable, IEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Index
	private Long objectId;

	@Index
	private Date createdAt;

	@Index
	private Date updatedAt;

	public GenericEntity() {
		super();
	}

	public GenericEntity(Long objectId) {
		this.objectId = objectId;
	}

	@Override
	public Long getObjectId() {
		return objectId;
	}

	@Override
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
