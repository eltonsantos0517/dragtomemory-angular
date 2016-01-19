package com.backend.base.security.entity;

import com.backend.base.controller.to.AccountTO;

public enum UserRole {
	USER, ADMIN;

	public UserAuthority asAuthorityFor(final AccountTO account) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		authority.setAccount(account);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
		case "ROLE_USER":
			return USER;
		case "ROLE_ADMIN":
			return ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}
}
