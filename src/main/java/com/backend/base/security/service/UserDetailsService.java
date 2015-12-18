package com.backend.base.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.base.security.entity.User;
import com.backend.base.security.entity.UserRole;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		//TODO Fazer a busca de um usuário por username/e-mail
		//final User user = userRepo.findByUsername(username);
		
		Set<UserRole> roles = new HashSet<UserRole>();
		roles.add(UserRole.ADMIN);
		roles.add(UserRole.USER);
		
		User user =  new User();
		user.setId(1l);
		user.setUsername("admin");
		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
		user.setRoles(roles);
		
		
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		detailsChecker.check(user);
		return user;
	}
}
