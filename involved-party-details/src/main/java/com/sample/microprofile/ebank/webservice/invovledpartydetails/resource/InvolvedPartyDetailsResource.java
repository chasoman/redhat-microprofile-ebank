package com.sample.microprofile.ebank.webservice.invovledpartydetails.resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class InvolvedPartyDetailsResource {
	
	/*
	 * This method is the provider for any requests to /{involvedPartyId} and returns the Involved Party details identified by its ID.
	 */
	@GET
	@Path("/{involvedPartyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInvolvedPartyDetails(@PathParam("involvedPartyId") String involvedPartyId) {
	
		Response response=null;	
		response = invokeDependentServices(involvedPartyId);			
		return response;
	}
	
	
	 /*
	 	This method invokes two REST resources:  /involved-party-profile and /involved-party-arrangements using RESTEasy client proxy and collates their responses 
	 	into a single response.
	 */
	private Response invokeDependentServices(String involvedPartyId) {
		
		JsonObject errorResponseJson = Json.createObjectBuilder()
				   .add("errors", Json.createArrayBuilder()
				   .add(Json.createObjectBuilder()
				   .add("code", "SAMPLE.EBANK.IPD.32600.404.1")
				   .add("description", "InvolvedParty "+involvedPartyId+" not found")))
				   .build();

		Response response = Response.status(Response.Status.NOT_FOUND)
											.entity(errorResponseJson)
											.build();
			
		String profileUrl = "http://localhost:28262/involved-party-profile/"+involvedPartyId;
			 
		String arrngUrl = "http://localhost:30080/involved-party-arrangements/"+involvedPartyId;
			
		try {

			
			 //Implements a Rest Client to invoke a microservice 
			//TODO: Instantiate a client builder object
			Client ipprofileclient = null;
			//TODO: Instantiate a web target proxy to 
			//	GET /involved-party-profile/{involvedPartyId} resource
			WebTarget ipprofTarget = null;
			//TODO: Invoke the target and get a response
			Response ipprofileResponse = null;
			//TODO: Instantiate a client builder object				
			Client iparrclient = null;
			//TODO: Instantiate a web target proxy to 
			//	GET /involved-party-arrangements/{involvedPartyId} resource
			WebTarget iparrTarget = null;
			//TODO: Invoke the target and get a response
			Response iparrResponse = null;
				
			if(ipprofileResponse.getStatus() == Response.Status.OK.getStatusCode() && iparrResponse.getStatus() == Response.Status.OK.getStatusCode()) {
				JsonObject ipprofResponseObj = ipprofileResponse.readEntity(JsonObject.class);
				JsonObject iparrResponseObj = iparrResponse.readEntity(JsonObject.class);
					
				JsonObjectBuilder finalResponseBuilder = Json.createObjectBuilder();
				ipprofResponseObj.entrySet().forEach(s -> finalResponseBuilder.add(s.getKey(), s.getValue()));
				iparrResponseObj.entrySet().forEach(s -> finalResponseBuilder.add(s.getKey(), s.getValue()));
				JsonObject finalResponse = finalResponseBuilder.build();
				response = Response.ok(finalResponse.toString()).build();
			}

			iparrResponse.close();
			ipprofileResponse.close();
			
		}catch(ProcessingException processingException) {
			errorResponseJson = Json.createObjectBuilder()
					   .add("errors", Json.createArrayBuilder()
					   .add(Json.createObjectBuilder()
					   .add("code", "SAMPLE.EBANK.IPD.32600.500.1")
					   .add("description", "Unknown technical exception ocurred in the service.")))
					   .build();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(errorResponseJson)
					.build();
		}
		return response;
	}
}
