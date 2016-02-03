package com.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;

import db.MongoDBClient;

@Path("/admin")
public class AdminHomeServlet {

	@GET
	@Produces("application/json")
	public Response getMessage() throws JSONException {

		JSONArray jsonArray = MongoDBClient.getMessages();

		return Response.status(200).entity("" + jsonArray).build();

	}
}
