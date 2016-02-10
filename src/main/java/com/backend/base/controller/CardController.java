package com.backend.base.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.base.controller.to.ApiResponse;
import com.backend.base.controller.to.CardTO;
import com.backend.base.model.entity.CardEntity;
import com.backend.base.model.service.CardService;
import com.google.api.server.spi.response.CollectionResponse;

@RestController
public class CardController {

	@RequestMapping(value = "/api/1/card", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getCards(@RequestParam(name = "limit", required = false) int limit,
			@RequestParam("cursor") String cursor, @RequestParam(name = "order") String order,
			@RequestParam(name = "filter") String filter) {

		try {			
			CardService service = new CardService();

			Long totalCount = service.count(filter);
			CollectionResponse<CardEntity> response = service.listCards(limit, cursor, order, filter);

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
	@RequestMapping(value = "/api/1/card/{objectId}", method = RequestMethod.GET)
	public CardEntity getCardById(@PathVariable("objectId") long objectId) {
		final CardService service = new CardService();
		return service.get(objectId);
	}

	@RequestMapping(value = "/api/1/card/{objectId}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteCard(@PathVariable("objectId") long objectId) {
		try {
			System.out.println("deleteCard");
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

	@RequestMapping(value = "/api/1/card", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> createCard(@RequestBody final CardTO to) {

		try {
			final CardService service = new CardService();
			CardTO persistedTO = new CardTO(service.saveCard(new CardEntity(to)));
			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, persistedTO);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/1/card", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> editCard(@RequestBody final CardTO to) {
		try {

			final CardService service = new CardService();
			CardTO persistedTO = new CardTO(service.editCard(new CardEntity(to)));
			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, persistedTO);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/1/card/done", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> done(@RequestBody final Long objectId) {
		try {
			final CardService service = new CardService();
			
			service.warnUsersAboutCardsToday();
			
			final CardTO persitedTO = new CardTO(service.done(getCardById(objectId)));
			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, persitedTO);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/api/1/job", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> processExpiretedCards() {
		try {			
			
			CardService service = new CardService();
			service.processExpiretedCards();
			service.warnUsersAboutCardsToday();
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
	
}
