package com.backend.base.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.base.controller.to.AccountTO;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.service.AccountService;
import com.backend.base.security.entity.UserRole;
import com.googlecode.objectify.Key;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public final AccountTO loadUserByUsername(final String email) throws UsernameNotFoundException {
		AccountService service = new AccountService();
		AccountEntity entity;

		final Key<AccountEntity> key = Key.create(AccountEntity.class, email);
		
		entity = service.get(key);
		if (entity == null) {
			throw new UsernameNotFoundException("Account not found");
		}

		Set<UserRole> roles = new HashSet<UserRole>();
		roles.add(UserRole.ADMIN);
		roles.add(UserRole.USER);

		final AccountTO account = new AccountTO(entity);
		account.setRoles(roles);

		detailsChecker.check(account);
		return account;
	}
}
