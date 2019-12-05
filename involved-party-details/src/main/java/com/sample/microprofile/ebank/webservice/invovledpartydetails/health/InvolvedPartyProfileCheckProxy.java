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
public class InvolvedPartyProfileCheckProxy implements HealthCheck{

	@Override
	public HealthCheckResponse call() {
		HealthCheckResponseBuilder healthCheckResponseBuilder = 
				HealthCheckResponse.named("involved-party-profile-proxy-check");
		HealthCheckResponse hcResponse = null;
		
		try {
			//Attempt to connect to the health resource of the involved-party-profile service
			//Use Rest client coding to achieve this
			Client ipprofCheckClient = ClientBuilder.newBuilder().build();
			WebTarget ipprofCheckTarget = ipprofCheckClient.target("http://localhost:28262/health");
			Response ipprofCheckResponse = ipprofCheckTarget.request().get();
			//Validate that a HTTP 200 response is returned
			if((ipprofCheckResponse == null) || 
					(ipprofCheckResponse.getStatus() != Response.Status.OK.getStatusCode())) {
				//If not a HTTP 200 response then declare the called service is DOWN
				hcResponse = healthCheckResponseBuilder.down().build();
			}else {
				//If you do get a HTTP 200 response then declare the called service status as UP
				hcResponse = healthCheckResponseBuilder.up().build();
			}
			ipprofCheckResponse.close();
		}catch(ProcessingException e) {
			//If you are unable to reach the called service then declare the status as DOWN
			hcResponse = healthCheckResponseBuilder.down().build();
		}	
		return hcResponse;
	}
}
