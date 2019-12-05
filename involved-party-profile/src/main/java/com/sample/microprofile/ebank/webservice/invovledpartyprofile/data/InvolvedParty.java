package com.sample.microprofile.ebank.webservice.invovledpartyprofile.data;

import javax.json.JsonObject;

public class InvolvedParty {
	
	private JsonObject involvedPartyUnderlying;
	
	public InvolvedParty(JsonObject parsedInvolvedParty) {
		this.involvedPartyUnderlying = parsedInvolvedParty;
	}
	
	public String getInvolvedParty() {
		return this.involvedPartyUnderlying.toString();
	}

}
