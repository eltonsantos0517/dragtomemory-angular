package com.backend.base.controller.to;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.backend.base.controller.to.generic.GenericTO;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.security.entity.UserAuthority;
import com.backend.base.security.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountTO extends GenericTO implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String email;
	private String profileImage;
	private String gender;
	private Date birthday;
	private String locale;
	private String password;
	private String passwordAgain;
	private String facebookToken;
	private long expires;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean accountEnabled;
	private Set<UserAuthority> authorities;

	public AccountTO() {
		super();
	}

	public AccountTO(AccountEntity entity) {
		super.setObjectId(entity.getObjectId());
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.email = entity.getEmail();
		if (entity.getProfileImage() != null) {
			this.profileImage = new String(entity.getProfileImage().getBytes(), StandardCharsets.UTF_8);
		}
		this.gender = entity.getGender();
		this.birthday = entity.getBirthday();
		this.locale = entity.getLocale();
		this.password = entity.getPassword();
		this.facebookToken = entity.getFacebookToken();
		this.expires = entity.getExpires();
		this.accountExpired = entity.isAccountExpired();
		this.accountLocked = entity.isAccountLocked();
		this.credentialsExpired = entity.isCredentialsExpired();
		this.accountEnabled = entity.isAccountEnabled();
		this.authorities = entity.getAuthorities();
	}

	public AccountTO(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public String getFacebookToken() {
		return facebookToken;
	}

	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public void setUsername(String username) {
		this.email = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	@JsonIgnore
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}

	// Use Roles as external API
	public Set<UserRole> getRoles() {
		Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
		if (authorities != null) {
			for (UserAuthority authority : authorities) {
				roles.add(UserRole.valueOf(authority));
			}
		}
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		for (UserRole role : roles) {
			grantRole(role);
		}
	}

	public void grantRole(UserRole role) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(role.asAuthorityFor(this));
	}

	public void revokeRole(UserRole role) {
		if (authorities != null) {
			authorities.remove(role.asAuthorityFor(this));
		}
	}

	public boolean hasRole(UserRole role) {
		return authorities.contains(role.asAuthorityFor(this));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return !accountEnabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	

}
