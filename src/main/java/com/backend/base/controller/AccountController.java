package com.backend.base.controller;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.base.controller.to.AccountTO;
import com.backend.base.controller.to.ApiResponse;
import com.backend.base.exception.InvalidEmailException;
import com.backend.base.exception.InvalidTokenException;
import com.backend.base.exception.MismatchedPasswordsException;
import com.backend.base.model.entity.AccountEntity;
import com.backend.base.model.entity.RecoveryTokenEntity;
import com.backend.base.model.service.AccountService;
import com.backend.base.security.entity.UserAuthentication;
import com.backend.base.security.jwt.TokenHandler;
import com.backend.base.security.service.UserDetailsService;
import com.google.api.server.spi.response.CollectionResponse;

@RestController
public class AccountController {

	private static final String AUTH_HEADER_NAME = "Authorization";

	@RequestMapping(value = "/api/users/current", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getCurrent() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof UserAuthentication) {
			
			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
					1l, 1, null, ((UserAuthentication) authentication).getDetails());

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		}
		
		
		// anonymous user support
		ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
				1l, 1, null, new AccountTO(authentication.getName()));

		return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/1/user", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getCurrenUser(@RequestParam(name = "limit", required = false) int limit,
			@RequestParam("cursor") String cursor, @RequestParam(name = "order") String order) {

		try {
			AccountService service = new AccountService();

			Long totalCount = service.count();
			CollectionResponse<AccountEntity> response = service.listPage(limit, cursor, order);
			if (response != null) {

				ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
						totalCount, response.getItems().size(), response.getNextPageToken(), response.getItems());

				return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
			}

			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, 0,
					null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

	@RequestMapping(value = "/api/1/user/{objectId}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("objectId") long objectId) {
		try {
			AccountService service = new AccountService();
			service.deleteById(objectId);

			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/1/user", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> createAccount(@RequestBody final AccountTO to) {

		try {
			AccountService service = new AccountService();
			Long id = service.saveAccount(to);
			to.setObjectId(id);
			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, to);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (InvalidEmailException | MismatchedPasswordsException | AccountException e) {
			ApiResponse ret = new ApiResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
					HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/1/user", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> editAccount(@RequestBody final AccountTO to) {

		try {

			if (to == null || to.getObjectId() == null) {
				ApiResponse ret = new ApiResponse("Invalid user", HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null, null);

				return new ResponseEntity<ApiResponse>(ret, HttpStatus.BAD_REQUEST);
			}

			AccountService service = new AccountService();
			Long id = service.saveAccount(to);
			to.setObjectId(id);
			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, to);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (InvalidEmailException | MismatchedPasswordsException | AccountException e) {
			ApiResponse ret = new ApiResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
					HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param email
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/api/1/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> forgotPassword(@RequestBody final String email) {
		AccountService service = new AccountService();
		ApiResponse ret = null;

		try {
			service.forgotPassword(email);
			ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null, null,
					"Email successfully sent");
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (InvalidEmailException e) {
			ret = new ApiResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
					HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null, null);
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ret = new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/api/1/recoveryPassword", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> recoveryPassword(@RequestBody final RecoveryTokenEntity recoveryToken) {
		AccountService service = new AccountService();
		ApiResponse ret = null;
		try {
			service.recoveryPassword(recoveryToken);
			ret = new ApiResponse("Successfully reset password", HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
					null, null, null, null);
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (InvalidTokenException | MismatchedPasswordsException e) {
			ret = new ApiResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
					HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null, null);
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ret = new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/facebookAuthenticate", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> facebookAuthenticate(@RequestBody final AccountTO to,
			HttpServletResponse response) {
		if(to.getEmail() == null || to.getEmail().isEmpty()){
			ApiResponse ret = new ApiResponse("It was not possible to login to facebook, please create your account", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null,
					null, null);
			return new ResponseEntity<ApiResponse> (ret, HttpStatus.BAD_REQUEST);
		}
		
		try {
			AccountService service = new AccountService();
			service.saveAccount(to);
			UserDetailsService udService = new UserDetailsService();

			AccountTO account = udService.loadUserByUsername(to.getEmail());

			TokenHandler tokenHandler = new TokenHandler("superSecreto123", udService);
			response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(account));

			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, null);
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse ret = new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);
			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
