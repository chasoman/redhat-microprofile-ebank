package com.sample.microprofile.ebank.webservice.invovledpartyarrangements.data;

import javax.json.JsonObject;

public class InvolvedPartyArrangements {
	
	private JsonObject involvedPartyArrangementsUnderlying;
	
	public InvolvedPartyArrangements(JsonObject parsedInvolvedPartyArrangements) {
		this.involvedPartyArrangementsUnderlying = parsedInvolvedPartyArrangements;
	}
	
	public String getInvolvedPartyArrangements() {
		return this.involvedPartyArrangementsUnderlying.toString();
	}

}
