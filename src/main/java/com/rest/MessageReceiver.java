package com.rest;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/sendmessage")
public class MessageReceiver {

	private static final Logger logger = LogManager.getLogger(MessageReceiver.class);

	@POST
	@Produces("application/json")
	public Response getMessage(String messageJSON) throws JSONException {
		JSONObject jsonObject = new JSONObject(messageJSON);

		String msg = null;
		String name = null;
		String gRecaptchaResponse = null;
		String secretKey = null;

		if (jsonObject.has("message")) {
			msg = (String) jsonObject.get("message");
		}
		if (jsonObject.has("name")) {
			name = (String) jsonObject.get("name");
		}
		if (jsonObject.has("g_recaptcha_response")) {
			gRecaptchaResponse = (String) jsonObject.get("g_recaptcha_response");
		}
		if (jsonObject.has("secretkey")) {
			secretKey = (String) jsonObject.get("secretkey");
		}

		logger.debug(msg + " " + name + " " + gRecaptchaResponse.length());

		boolean verify = false;

		try {
			verify = VerifyRecaptcha.verify(gRecaptchaResponse, secretKey);
			logger.debug("Verify recaptcha: " + verify);
		} catch (IOException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}

		if (verify) {

			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "successfully sent! thank you!");
			String result = "" + jsonObject2;

			return Response.status(200).entity(result).build();
		} else {
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "are you robot? check box please!");
			String result = "" + jsonObject2;

			return Response.status(401).entity(result).build();

		}
	}

}
