package com.sample.microprofile.ebank.webservice.invovledpartydetails.resource;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;


@Path("/")

public class InvolvedPartyDetailsResource {
	
	@Inject
	private Config config;
	
	@Inject
	@ConfigProperty(name= "profilePort", defaultValue= "8081")
	// retrieve a specific config property
	private String profilePort;
	
	private String arrangementPort;
	
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
	 	This is a generic method to create an error response object which is returned to the consumer. 
	 */
	private Response createErrorResponse(String codeValue,String descriptionValue,String responseStatus) {
		JsonObject errorResponseJson = Json.createObjectBuilder()
				   						   .add("errors", Json.createArrayBuilder()
				   						   .add(Json.createObjectBuilder()
				   						   .add("code", codeValue)
				   						   .add("description", descriptionValue)))
				   						   .build();

		Response response = Response.status(Response.Status.valueOf(responseStatus))
									.entity(errorResponseJson)
									.build();
		
		return response;
	}
	
	 /*
	 	This method invokes two REST resources:  /involved-party-profile and /involved-party-arrangements using RESTEasy client proxy and collates their responses 
	 	into a single response.
	 */
	private Response invokeDependentServices(String involvedPartyId) {
		
			Response response = null;
			
			String profileUrl = "http://localhost:"+profilePort+"/involved-party-profile/"+involvedPartyId;
			 
			//retrieve the required property from the injected config object
			arrangementPort = config.getValue("arrangementPort", String.class);
			String arrngUrl = "http://localhost:"+arrangementPort+"/involved-party-arrangements/"+involvedPartyId;
			
			Client ipprofileclient = ClientBuilder.newBuilder().build();
			WebTarget ipprofTarget = ipprofileclient.target(profileUrl);
			Response ipprofileResponse = ipprofTarget.request().get();
							
			Client iparrclient = ClientBuilder.newBuilder().build();
			WebTarget iparrTarget = iparrclient.target(arrngUrl);
			Response iparrResponse = iparrTarget.request().get();
				
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
					
			return response;
		}
		
	
	
	/*
 		TODO
 		We do not wish to give up if we do not get a response from a call to another microservice to fetch some data. We need to be able to return the data 
 		from another source if possible.  
 		In this scenario, using the correct Fault Tolerance annotation,switch the call to a backup service (in our case the method ‘getInvolvedPartyDetailsfromCache’)  
	*/
	
	@GET
	@Path("/{involvedPartyId}/faulttolerance")
	@Produces(MediaType.APPLICATION_JSON)
	@Timeout(value=10, unit = ChronoUnit.SECONDS)
	public Response getInvolvedPartyDetailsWithFT(@PathParam("involvedPartyId") String involvedPartyId) {
		Response response=null;	
		try {
			TimeUnit.SECONDS.sleep(10);
			response = invokeDependentServices(involvedPartyId);			
		}catch(InterruptedException e) {
			response = createErrorResponse("SAMPLE.EBANK.IPDetails.32685.500.1", "Sleep Interrupted", "INTERNAL_SERVER_ERROR");			
		}
			
		return response;
	}
	/*
		This method is used as an alternative to getInvolvedPartyDetailsWithFT method.
	*/
	public Response getInvolvedPartyDetailsFromCache(String involvedPartyId){
		JsonObject responseJson = Json.createObjectBuilder()
									  .add("response", "Some cached response")
									  .build();
	
		return Response.status(Response.Status.OK).entity(responseJson).build();
	}
}
