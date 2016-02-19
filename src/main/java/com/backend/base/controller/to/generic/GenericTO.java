package com.backend.base.controller.to.generic;

import java.io.Serializable;
import java.util.Date;

public abstract class GenericTO implements Serializable, ITO<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long objectId;

	private Date createdAt;

	private Date updatedAt;

	public GenericTO() {
		super();
	}

	public GenericTO(Long objectId) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericTO other = (GenericTO) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GenericTO [objectId=" + objectId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
