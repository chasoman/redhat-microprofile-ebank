package com.sample.microprofile.ebank.webservice.invovledpartyarrangements.data;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

public class InvolvedPartyArrangementsParser {
	
	private String invovledPartyId;
	
	public InvolvedPartyArrangementsParser(String invovledPartyId) {
		this.invovledPartyId = invovledPartyId;
	}
	
	public InvolvedPartyArrangements parse() {
		String fileName = "/" + this.invovledPartyId + ".arrangements.json";
		InputStream is = this.getClass().getResourceAsStream(fileName);
		InvolvedPartyArrangements ipa = null;
		if(is != null) {
			JsonReaderFactory factory = Json.createReaderFactory(null);
			JsonReader reader = factory.createReader(is);
			ipa = new InvolvedPartyArrangements(reader.readObject());
		}
		return ipa;
	}

}
