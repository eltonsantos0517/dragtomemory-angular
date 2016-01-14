package com.backend.base.security.config;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.backend.base.controller.to.AccountTO;
import com.backend.base.exception.InvalidEmailException;
import com.backend.base.model.service.AccountService;
import com.backend.base.security.entity.User;
import com.backend.base.security.entity.UserAuthentication;
import com.backend.base.security.jwt.TokenAuthenticationService;
import com.backend.base.security.service.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

class StatelessRegisterFilter extends AbstractAuthenticationProcessingFilter {

	private final TokenAuthenticationService tokenAuthenticationService;
	private final UserDetailsService userDetailsService;

	protected StatelessRegisterFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
			UserDetailsService userDetailsService, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userDetailsService = userDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		final AccountTO to = new ObjectMapper().readValue(request.getInputStream(), AccountTO.class);

		try {
			AccountService service = new AccountService();
			service.saveAccount(to, false);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidEmailException e) {
			//TODO Exibir ao usuário que ele já é registrado
			e.printStackTrace();
		}

		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				to.getEmail(), to.getPassword());
		return getAuthenticationManager().authenticate(loginToken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		// Lookup the complete User object from the database and create an
		// Authentication for it
		final User authenticatedUser = userDetailsService.loadUserByUsername(authentication.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}
}