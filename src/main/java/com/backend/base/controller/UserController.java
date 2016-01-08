package com.backend.base.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.base.controller.to.AccountTO;
import com.backend.base.controller.to.ApiResponse;
import com.backend.base.exception.InvalidEmailException;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.service.AccountService;
import com.backend.base.security.entity.User;
import com.backend.base.security.entity.UserAuthentication;
import com.backend.base.security.entity.UserRole;

@RestController
public class UserController {

	@RequestMapping(value = "/api/users/current", method = RequestMethod.GET)
	public User getCurrent() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof UserAuthentication) {
			return ((UserAuthentication) authentication).getDetails();
		}
		return new User(authentication.getName()); // anonymous user support
	}

	@RequestMapping(value = "/api/1/user", method = RequestMethod.GET)
	public List<AccountEntity> getCurrenUser() {
		AccountService service = new AccountService();

		return service.listAll();
	}

	/**
	 * 
	 * @param objectId
	 * @return AccountEntity
	 */
	@RequestMapping(value = "/api/1/user/{objectId}", method = RequestMethod.GET)
	public AccountEntity getUserById(@PathVariable("objectId") long objectId) {
		AccountService service = new AccountService();

		return service.get(objectId);
	}

	@RequestMapping(value = "/api/1/user", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> saveUser(@RequestBody final AccountTO user) {

		try {
			AccountTO to = new AccountTO();
			to.setObjectId(user.getObjectId());
			to.setEmail(user.getEmail());
			to.setFirstName(user.getFirstName());
			to.setPassword(user.getPassword());
			to.setPasswordAgain(user.getPasswordAgain());

			AccountService service = new AccountService();

			if (to.getObjectId() == null) {
				service.createAccount(to);
			} else {
				service.changeAccount(to);
			}

			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param email
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/api/1/forgotPassword", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> forgotPassword(@RequestBody final String email) {
		AccountService service = new AccountService();

		try {
			service.forgotPassword(email);
			return new ResponseEntity<String>("Email successfully sent", HttpStatus.OK);
		} catch (InvalidEmailException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/api/users/current", method = RequestMethod.PATCH)
	public ResponseEntity<String> changePassword(@RequestBody final User user) {
		//final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = null;// userRepository.findByUsername(authentication.getName());

		if (user.getNewPassword() == null || user.getNewPassword().length() < 4) {
			return new ResponseEntity<String>("new password to short", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		if (!pwEncoder.matches(user.getPassword(), currentUser.getPassword())) {
			return new ResponseEntity<String>("old password mismatch", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		currentUser.setPassword(pwEncoder.encode(user.getNewPassword()));
		// userRepository.saveAndFlush(currentUser);
		return new ResponseEntity<String>("password changed", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/users/{user}/grant/role/{role}", method = RequestMethod.POST)
	public ResponseEntity<String> grantRole(@PathVariable User user, @PathVariable UserRole role) {
		if (user == null) {
			return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.grantRole(role);
		// userRepository.saveAndFlush(user);
		return new ResponseEntity<String>("role granted", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/users/{user}/revoke/role/{role}", method = RequestMethod.POST)
	public ResponseEntity<String> revokeRole(@PathVariable User user, @PathVariable UserRole role) {
		if (user == null) {
			return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.revokeRole(role);
		// userRepository.saveAndFlush(user);
		return new ResponseEntity<String>("role revoked", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/users", method = RequestMethod.GET)
	public List<User> list() {
		return null;// userRepository.findAll();
	}
}
