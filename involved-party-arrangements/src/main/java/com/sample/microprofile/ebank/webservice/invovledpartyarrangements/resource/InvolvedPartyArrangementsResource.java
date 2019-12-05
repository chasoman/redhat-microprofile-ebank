package com.sample.microprofile.ebank.webservice.invovledpartyarrangements.resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sample.microprofile.ebank.webservice.invovledpartyarrangements.data.InvolvedPartyArrangements;
import com.sample.microprofile.ebank.webservice.invovledpartyarrangements.data.InvolvedPartyArrangementsParser;

@Path("/")
public class InvolvedPartyArrangementsResource {
	
	//TODO: Inject the JAX-RS GET annotation
	//	to nominate this method as a HTTP GET resource.
	
	//TODO: Inject a JAX-RS Path annotation to denote the resource path
	//	and accept a path parameter.
	
	//TODO: Inject a JAX-RS Produces annotation to denote
	//	this resource as a producer of JSON responses.
	
	//TODO: Inject a path parameter annotation in the method signature.
	public Response getInvolvedPartyArrangements(String involvedPartyId) {
		
		//TODO: Implement a Json Object Builder 
		// and create an error response Json object.
		JsonObject errorResponseJson = null;
		
		Response response = Response.status(Response.Status.NOT_FOUND)
							.entity(errorResponseJson)
							.build();
		InvolvedPartyArrangementsParser ipArrParser = new InvolvedPartyArrangementsParser(involvedPartyId);
		InvolvedPartyArrangements ipArr = ipArrParser.parse();
		
		if(ipArr != null) {
			response = Response.ok(ipArr.getInvolvedPartyArrangements()).build();
		}
		
		return response;
	}
}
