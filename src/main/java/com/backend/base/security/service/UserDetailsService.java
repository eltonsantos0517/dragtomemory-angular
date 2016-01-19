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

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public final AccountTO loadUserByUsername(String email) throws UsernameNotFoundException {
		AccountService service = new AccountService();
		AccountEntity entity;

		int attempts = 0;
		do {

			entity = service.getByColumn("email", email);
			if (entity == null) {
				if (attempts < 4) {
					attempts++;
				} else {
					throw new UsernameNotFoundException("Account not found");
				}
			} else {
				break;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (true);

		Set<UserRole> roles = new HashSet<UserRole>();
		roles.add(UserRole.ADMIN);
		roles.add(UserRole.USER);

		final AccountTO account = new AccountTO();
		account.setUsername(entity.getEmail());
		account.setObjectId(entity.getObjectId());
		account.setPassword(entity.getPassword());
		account.setRoles(roles);

		detailsChecker.check(account);
		return account;
	}
}
