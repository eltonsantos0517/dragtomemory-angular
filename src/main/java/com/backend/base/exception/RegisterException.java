package com.backend.base.exception;

import org.springframework.security.core.AuthenticationException;

public class RegisterException extends AuthenticationException {

	public RegisterException(String msg) {
		super(msg);
	}

}
