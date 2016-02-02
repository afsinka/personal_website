package com.rest;

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
		String msg = (String) jsonObject.get("message");

		System.out.println(msg);
		logger.debug("message: " + msg);

		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("result", "success");
		String result = "" + jsonObject2;

		return Response.status(200).entity(result).build();
	}

}
