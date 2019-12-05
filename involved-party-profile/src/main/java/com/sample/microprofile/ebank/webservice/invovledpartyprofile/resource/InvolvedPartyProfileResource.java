package com.sample.microprofile.ebank.webservice.invovledpartyprofile.resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import com.sample.microprofile.ebank.webservice.invovledpartyprofile.data.InvolvedParty;
import com.sample.microprofile.ebank.webservice.invovledpartyprofile.data.InvolvedPartyParser;

@Path("/")
public class InvolvedPartyProfileResource {
	
	@Context
	private HttpServletRequest request;
	
	@GET
	@Path("/{involvedPartyId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(absolute = true
	  , name = "involved_party_profile_getInvolvedPartyProfile_timer"
	  , displayName = "involved_party_profile_getInvolvedPartyProfile_timer"
	  , unit = MetricUnits.MILLISECONDS)
	@Counted(absolute = true
	  , name = "involved_party_profile_getInvolvedPartyProfile_counter"
	  , displayName = "involved_party_profile_getInvolvedPartyProfile_counter"
	  , monotonic = true
	  , unit = MetricUnits.NONE)
	public Response getInvolvedPartyProfile(@PathParam("involvedPartyId") String involvedPartyId) {
		
		JsonObject errorResponseJson = Json.createObjectBuilder()
								   		   .add("errors", Json.createArrayBuilder()
										   .add(Json.createObjectBuilder()
										   .add("code", "SAMPLE.EBANK.IPP.32684.404.1")
										   .add("description", "Involved Party " + involvedPartyId + " Not Found")))
								           .build();
		
		Response response = Response.status(Response.Status.NOT_FOUND)
				                    .entity(errorResponseJson)
				                    .build();

		InvolvedPartyParser parser = new InvolvedPartyParser(involvedPartyId);
		InvolvedParty party = parser.parse();

		if(party != null) {
			response = Response.ok(party.getInvolvedParty()).build();
		}

		return response;
	}

}
