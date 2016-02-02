package com.backend.base.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.base.controller.to.ApiResponse;
import com.backend.base.controller.to.PushTO;
import com.backend.base.model.service.GcmService;

@RestController
public class PushNotificationController {

	@RequestMapping(value = "/api/1/sendMessage", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> createCard(@RequestBody final PushTO to) {

		try {
			
			final GcmService service = new GcmService();
			service.sendMessage(to);
			ApiResponse ret = new ApiResponse(null, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null,
					null, "Mensagem enviada com sucesso");

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			ApiResponse ret = new ApiResponse("Sorry, something bad happened", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null, null);

			return new ResponseEntity<ApiResponse>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
}
