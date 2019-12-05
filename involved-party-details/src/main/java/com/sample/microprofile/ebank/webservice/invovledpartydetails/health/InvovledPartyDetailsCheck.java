package com.sample.microprofile.ebank.webservice.invovledpartydetails.health;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class InvovledPartyDetailsCheck implements HealthCheck{

	@Override
	public HealthCheckResponse call() {
		HealthCheckResponseBuilder healthCheckResponseBuilder = 
					HealthCheckResponse.named("involved-party-details-check");
		HealthCheckResponse hcResponse = null;
		boolean overallStatusUp = true;

		try {
			Client ipprofCheckClient = ClientBuilder.newBuilder().build();
			WebTarget ipprofCheckTarget = ipprofCheckClient.target("http://localhost:8081/health");
			Response ipprofCheckResponse = ipprofCheckTarget.request().get();
			if((ipprofCheckResponse == null) || 
					(ipprofCheckResponse.getStatus() != Response.Status.OK.getStatusCode())) {
				healthCheckResponseBuilder.withData("involved-party-profile-check", "DOWN");
				overallStatusUp = false;
			}else {
				healthCheckResponseBuilder.withData("involved-party-profile-check", "UP");
			}
		}catch(ProcessingException e) {
			healthCheckResponseBuilder.withData("involved-party-profile-check", "DOWN");
			overallStatusUp = false;
		}
		
		
		try {
			Client iparrCheckClient = ClientBuilder.newBuilder().build();
			WebTarget iparrCheckTarget = iparrCheckClient.target("http://localhost:8082/health");
			Response iparrCheckResponse = iparrCheckTarget.request().get();
			if((iparrCheckResponse == null) || 
					(iparrCheckResponse.getStatus() != Response.Status.OK.getStatusCode())) {
				healthCheckResponseBuilder.withData("involved-party-arrangements-check", "DOWN");
				overallStatusUp = false;
			}else {
				healthCheckResponseBuilder.withData("involved-party-arrangements-check", "UP");
			}
		}catch(ProcessingException e) {
			healthCheckResponseBuilder.withData("involved-party-arrangements-check", "DOWN");
			overallStatusUp = false;
		}
		
		if(overallStatusUp) {
			hcResponse = healthCheckResponseBuilder.up().build();
		}else {
			hcResponse = healthCheckResponseBuilder.down().build();
		}
		return hcResponse;
		
	}

}
