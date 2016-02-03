package com.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import db.MongoDBClient;

@Path("/auth")
public class AuthServlet {

	private static final Logger logger = LogManager.getLogger(MessageReceiver.class);

	@POST
	@Produces("application/json")
	public Response getMessage(String messageJSON) throws JSONException {

		JSONObject jsonObject = new JSONObject(messageJSON);

		String username = (String) jsonObject.get("username");
		String password = (String) jsonObject.get("password");

		logger.debug("Login attempt: " + username + " " + password);

		boolean auth = MongoDBClient.auth(username, password);

		if (auth) {
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("success", "true");
			String result = "" + resultJSON;

			return Response.status(200).entity(result).build();
		} else {
			JSONObject failJSON = new JSONObject();
			failJSON.put("success", "false");
			String result = "" + failJSON;

			return Response.status(401).entity(result).build();
		}

	}
}
