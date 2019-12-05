package com.sample.microprofile.ebank.webservice.invovledpartydetails.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

//TODO: Inject the microprofile Health annotation

@ApplicationScoped
//TODO: Implement the microprofile HealthCheck interface
public class InvovledPartyDetailsCheck{
/*
 * This method reports overall health of the involved-party-details service
 * as either UP or DOWN depending on the health of called services.
 **/
	@Override
	public HealthCheckResponse call() {
		//TODO: Instantiate a HealthCheckResponseBuilder
		// having a name "involved-party-details-check"
		HealthCheckResponseBuilder healthCheckResponseBuilder = null;
		//TODO: Set the health check response status as UP
		HealthCheckResponse hcResponse = null;
		
		return hcResponse;
	}
}
