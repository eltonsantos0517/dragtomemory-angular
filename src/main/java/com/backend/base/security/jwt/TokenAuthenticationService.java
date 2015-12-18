package com.backend.base.security.jwt;

import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import com.backend.base.security.entity.User;
import com.backend.base.security.entity.UserAuthentication;
import com.backend.base.security.service.UserDetailsService;

public class TokenAuthenticationService {

	private static final String AUTH_HEADER_NAME = "Authorization";
	private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
	private static final long ONE_DAY = 1000 * 60 * 60 * 24 * 1;

	private final TokenHandler tokenHandler;

	/*
	 * @Autowired public
	 * TokenAuthenticationService_new(@Value("${token.secret}") String secret) {
	 * tokenHandler = new
	 * TokenHandler_new(DatatypeConverter.parseBase64Binary(secret)); }
	 */

	public TokenAuthenticationService(String secret, UserDetailsService userService) {
		tokenHandler = new TokenHandler(secret, userService);
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		user.setExpires(System.currentTimeMillis() + ONE_DAY);
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String token;
		try {
			token = getToken(request);
		} catch (ServletException e) {
			e.printStackTrace();
			return null;
		}

		if (token != null) {
			final User user = tokenHandler.parseUserFromToken(token);
			if (user != null) {
				return new UserAuthentication(user);
			}
		}
		return null;
	}

	private String getToken(HttpServletRequest httpRequest) throws ServletException {
		String token = null;
		final String authorizationHeader = httpRequest.getHeader(AUTH_HEADER_NAME);
		if (authorizationHeader == null) {
			return null;
		}

		String[] parts = authorizationHeader.split(" ");
		if (parts.length != 2) {
			return null;
		}

		String scheme = parts[0];
		String credentials = parts[1];

		Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(scheme).matches()) {
			token = credentials;
		}
		return token;
	}
}
