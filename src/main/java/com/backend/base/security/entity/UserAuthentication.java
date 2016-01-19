package com.backend.base.security.entity;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.backend.base.controller.to.AccountTO;

public class UserAuthentication implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final AccountTO account;
	private boolean authenticated = true;

	public UserAuthentication(AccountTO account) {
		this.account = account;
	}

	@Override
	public String getName() {
		return account.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return account.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return account.getPassword();
	}

	@Override
	public AccountTO getDetails() {
		return account;
	}

	@Override
	public Object getPrincipal() {
		return account.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
}
