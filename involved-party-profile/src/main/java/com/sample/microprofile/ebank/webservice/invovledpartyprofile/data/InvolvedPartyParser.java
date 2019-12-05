package com.sample.microprofile.ebank.webservice.invovledpartyprofile.data;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

public class InvolvedPartyParser {
	
	private String invovledPartyId;
	
	public InvolvedPartyParser(String invovledPartyId) {
		this.invovledPartyId = invovledPartyId;
	}
	
	public InvolvedParty parse() {
		String fileName = "/" + this.invovledPartyId + ".json";
		InputStream is = this.getClass().getResourceAsStream(fileName);
		InvolvedParty ip = null;
		if(is != null) {
			JsonReaderFactory factory = Json.createReaderFactory(null);
			JsonReader reader = factory.createReader(is);
			ip = new InvolvedParty(reader.readObject());
		}
		return ip;
	}
}
