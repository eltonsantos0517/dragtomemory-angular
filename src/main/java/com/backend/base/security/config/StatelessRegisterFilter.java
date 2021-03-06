package com.backend.base.security.config;

import java.io.IOException;

import javax.security.auth.login.AccountException;
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
import com.backend.base.exception.MismatchedPasswordsException;
import com.backend.base.exception.RegisterException;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.service.AccountService;
import com.backend.base.security.entity.UserAuthentication;
import com.backend.base.security.jwt.TokenAuthenticationService;
import com.backend.base.security.service.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.Key;

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
			final Key<AccountEntity> key = Key.create(AccountEntity.class, to.getEmail());
			AccountEntity entity = service.get(key);
			if(entity!= null){
				throw new AccountException("An account already exists with the email");
			}
			service.saveAccount(to);

			final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
					to.getEmail(), to.getPassword());
			return getAuthenticationManager().authenticate(loginToken);
		} catch (InvalidEmailException | MismatchedPasswordsException | AccountException e) {
			throw new RegisterException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		// Lookup the complete User object from the database and create an
		// Authentication for it
		final AccountTO authenticatedUser = userDetailsService.loadUserByUsername(authentication.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}
}