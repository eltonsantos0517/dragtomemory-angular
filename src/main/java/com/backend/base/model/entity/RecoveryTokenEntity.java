package com.backend.base.model.entity;

import java.io.Serializable;

import com.backend.base.model.entity.generic.GenericEntity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

@Entity
public class RecoveryTokenEntity extends GenericEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Index
	private String token;

	@Index
	private long userId;

	@Index
	private int validate;

	@Index
	private String newPassword;

	@Serialize
	private String newPasswordAgain;

	@Index
	private boolean active;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getValidate() {
		return validate;
	}

	public void setValidate(int validate) {
		this.validate = validate;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
