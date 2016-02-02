package com.backend.base.model.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.backend.base.controller.to.PushTO;
import com.google.gson.JsonObject;



/**
 * @author Bruno
 * 
 *         Parameters and doc: https://developers.google.com/cloud-messaging/http-server-ref
 *
 */
public class GcmService {
	
	private static final String API_KEY = "AIzaSyCLuxmjXDKGsppNGbOKLGnm_oHcSnaqWxg";

	public String sendMessage(PushTO pushTO) throws Exception {
		try {

			this.validatePushObject(pushTO);

			// TODO Get apiKey of the project
			

			// Prepare JSON containing the GCM message content. What to send and where to send.
			JsonObject jGcmData = new JsonObject();
			JsonObject jData = new JsonObject();

			jData.addProperty("message", pushTO.getMessage());

			// Where to send GCM message.
			if (pushTO.getTo() != null && !pushTO.getTo().isEmpty()) {
				jGcmData.addProperty("to", pushTO.getTo());
			} else {
				jGcmData.addProperty("to", "/topics/global");
			}

			// What to send in GCM message.
			jGcmData.add("data", jData);

			// Create connection to send GCM Message request.
			URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", "key=" + API_KEY);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// Send GCM message content.
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(jGcmData.toString().getBytes());

			// Read GCM response.
			InputStream inputStream = conn.getInputStream();
			String resp = IOUtils.toString(inputStream);

			return resp;

		} catch (Exception e) {
			System.out.println("Unable to send GCM message.");
			e.printStackTrace();
			throw e;
		}
	}

//	private PushConfigurationTO obtainPushConfiguration(ProjectEntity projectEntity) {
//		PushConfigurationService service = new PushConfigurationService(projectEntity.getObjectId().toString());
//		return service.getByProjectId(projectEntity.getObjectId());
//	}

	private void validatePushObject(PushTO pushTO) {
		// TODO Auto-generated method stub

	}
}
