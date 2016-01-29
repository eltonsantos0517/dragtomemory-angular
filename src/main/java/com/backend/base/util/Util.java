package com.backend.base.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

public class Util {

	private static Pattern pattern;
	private static Matcher matcher;

	private static final String EMAIL_WITH_SPECIAL_CARACTER_PATTERN = "^[_A-Za-z0-9-!#$%&()*+-/?_{|}\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean isValidEmail(final String email) {

		pattern = Pattern.compile(EMAIL_WITH_SPECIAL_CARACTER_PATTERN);
		matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			return false;
		}

		return true;
	}

	public static Date getDateOfNextRevision(final int stage) throws Exception {

		int plusDays = 0;

		switch (stage) {
			case 1:
				plusDays = 1;
				break;
			case 2:
				plusDays = 3;
				break;
			case 3:
				plusDays = 5;
				break;
			case 4:
				plusDays = 13;
				break;
			default:
				throw new Exception("O lembrete já foi concluido");
		}

		return new DateTime().plusDays(plusDays).toDate();
	}

	private Util() {

	}

}
