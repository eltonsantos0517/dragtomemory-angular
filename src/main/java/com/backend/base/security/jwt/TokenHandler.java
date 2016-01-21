package com.backend.base.security.jwt;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTSigner.Options;
import com.auth0.jwt.JWTVerifier;
import com.backend.base.controller.to.AccountTO;
import com.backend.base.security.service.UserDetailsService;

public class TokenHandler {

	private final UserDetailsService userService;
	private final JWTVerifier jwtVerifier;
	private final JWTSigner signer;

	public TokenHandler(String secret, UserDetailsService userService) {
		this.userService = userService;
		this.jwtVerifier = new JWTVerifier(secret);
		this.signer = new JWTSigner(secret);
	}

	public AccountTO parseUserFromToken(String token) {
		String username = null;
		try {
			Map<String, Object> decoded = jwtVerifier.verify(token);
			username = (String) decoded.get("username");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return userService.loadUserByUsername(username);
	}

	public String createTokenForUser(AccountTO account) {

		Options op = new Options();
		op.setAlgorithm(Algorithm.HS512);

		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("username", account.getUsername());
		claims.put("firstName", account.getFirstName());
		
		//TODO Setar roles
		claims.put("role", account.getRoles());
		
		
		String token = signer.sign(claims, op);

		return token;
	}

}
