package com.backend.base.controller.to;

import java.io.Serializable;

import com.backend.base.controller.to.generic.GenericTO;

public class AccountTO extends GenericTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String passwordAgain;
	private String facebookToken;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public String getFacebookToken() {
		return facebookToken;
	}

	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}

	
}
