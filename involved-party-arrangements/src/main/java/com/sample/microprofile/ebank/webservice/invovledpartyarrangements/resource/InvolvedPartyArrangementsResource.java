package com.sample.microprofile.ebank.webservice.invovledpartyarrangements.resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Metered;

import com.sample.microprofile.ebank.webservice.invovledpartyarrangements.data.InvolvedPartyArrangements;
import com.sample.microprofile.ebank.webservice.invovledpartyarrangements.data.InvolvedPartyArrangementsParser;

@Path("/")
public class InvolvedPartyArrangementsResource {

	@GET
	@Path("/{involvedPartyId}")
	@Produces(MediaType.APPLICATION_JSON)
	//TODO: Implement a Metered annotation having
	// name involved_party_arrangements_getInvolvedPartyArrangements_meter
	public Response getInvolvedPartyArrangements(@PathParam("involvedPartyId") String involvedPartyId) {
		JsonObject errorResponseJson = Json.createObjectBuilder()
									   .add("errors", Json.createArrayBuilder()
											.add(Json.createObjectBuilder()
													.add("code", "SAMPLE.EBANK.IPArr.32684.404.1")
													.add("description", "Involved Party "
																		+ involvedPartyId
																		+ " Not Found")))
									   .build();
		
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
