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
public class InvolvedPartyArrangementsCheckProxy implements HealthCheck{

	@Override
	public HealthCheckResponse call() {
		HealthCheckResponseBuilder healthCheckResponseBuilder = 
				HealthCheckResponse.named("involved-party-arrangements-proxy-check");
		HealthCheckResponse hcResponse = null;
		
		try {
			//Attempt to connect to the health resource of the involved-party-arrangements service
			//Use Rest client coding to achieve this
			Client iparrCheckClient = ClientBuilder.newBuilder().build();
			WebTarget iparrCheckTarget = iparrCheckClient.target("http://localhost:30080/health");
			Response iparrCheckResponse = iparrCheckTarget.request().get();
			//Validate that a HTTP 200 response is returned
			if((iparrCheckResponse == null) || 
					(iparrCheckResponse.getStatus() != Response.Status.OK.getStatusCode())) {
				//TODO: If you do not get an HTTP 200 success response
				// then set the health check response to DOWN
				hcResponse = null;
			}else {
				//TODO: If you do get an HTTP 200 success response
				// then set the health check response to UP
				hcResponse = null;
			}
			iparrCheckResponse.close();
		}catch(ProcessingException e) {
			//TODO: In the event of an exception
			// set the health check response to DOWN
			hcResponse = null;
		}	
		return hcResponse;
	}

}
