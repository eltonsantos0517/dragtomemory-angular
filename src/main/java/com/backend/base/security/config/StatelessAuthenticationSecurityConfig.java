package com.backend.base.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.base.security.jwt.TokenAuthenticationService;
import com.backend.base.security.service.UserDetailsService;

@EnableWebSecurity
@Configuration
@Order(1)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationSecurityConfig() {
		super(true);
		this.userDetailsService = new UserDetailsService();
		this.tokenAuthenticationService = new TokenAuthenticationService("superSecreto", this.userDetailsService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().and().anonymous().and().servletApi().and()
				.authorizeRequests()

				// allow anonymous resource requests
				.antMatchers("/login**").permitAll().antMatchers("/favicon.ico")
				.permitAll()
				.antMatchers("/resources**")
				.permitAll()
				
				//TODO Remove
				.antMatchers("/vendors/**")
				.permitAll()
				
				.antMatchers("**/*.html").permitAll()
				
				.antMatchers("**/*.css").permitAll()
				.antMatchers("**/*.js").permitAll()
				
				//allow anonymous POSTs to login
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()

				// allow anonymous POSTs to login
				/*
				 * .antMatchers(HttpMethod.POST, "/login") .permitAll()
				 */

				// allow anonymous GETs to API
				/*
				 * .antMatchers(HttpMethod.GET, "/api/**") .permitAll()
				 */

				// defined Admin only API area
				/*
				 * .antMatchers("/admin/**") .hasRole("ADMIN")
				 */

				// all other request need to be authenticated
				.anyRequest()
				.hasRole("USER")
				.and()
				
				

				// custom JSON based authentication by POST of
				// {"username":"<name>","password":"<password>"} which sets the
				// token header upon authentication
				.addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userDetailsService, authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				// custom Token based authentication based on the header
				// previously given to the client
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);

		http.headers().cacheControl();

		http.formLogin().loginPage("/login").permitAll();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}
	
}
