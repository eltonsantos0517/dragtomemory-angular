package com.backend.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	private static Pattern pattern;
	private static Matcher matcher;
	
	private static final String EMAIL_WITH_SPECIAL_CARACTER_PATTERN =
	        "^[_A-Za-z0-9-!#$%&()*+-/?_{|}\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean isValidEmail(final String email){
		
		pattern = Pattern.compile(EMAIL_WITH_SPECIAL_CARACTER_PATTERN);
		matcher = pattern.matcher(email);
		
		if (!matcher.matches()) {
			return false;
		}
		
		return true;
	}
	
	private Util(){
		
	}

}
