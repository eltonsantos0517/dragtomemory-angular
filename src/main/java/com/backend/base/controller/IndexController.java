package com.backend.base.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {// Serves main index.html

	@RequestMapping(method = RequestMethod.GET)
	public String getIndexPage() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String getLogin() {
		return "login";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/unauthorized")
	public String getUnauthorized() {
		return "unauthorized";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/**")
	public String getHome() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/recovery-password/{token}")
	public String getRecoveryPassword(@PathVariable("token") String token) {
		return "recovery-password";
	}


	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
