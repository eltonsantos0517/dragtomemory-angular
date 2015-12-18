package com.backend.base.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.service.AccountService;
import com.backend.base.security.entity.User;
import com.backend.base.security.entity.UserRole;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {

		AccountService service = new AccountService();
		AccountEntity entity = service.getByColumn("email", username);

		if (entity == null) {
			throw new UsernameNotFoundException("Account not found");
		}

		Set<UserRole> roles = new HashSet<UserRole>();
		roles.add(UserRole.ADMIN);
		roles.add(UserRole.USER);

		final User user = new User();
		user.setUsername(entity.getEmail());
		user.setId(entity.getObjectId());
		user.setPassword(entity.getPassword());
		user.setRoles(roles);

		detailsChecker.check(user);
		return user;
	}
}
