package com.backend.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bruno
 *
 */
public class EmailValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Validate hex with regular expression
	 * 
	 * @param email
	 *            email for validation
	 * @return true valid email, false invalid email
	 */
	public static boolean validate(final String email) {

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

}
