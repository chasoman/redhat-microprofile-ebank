package com.sample.microprofile.ebank.webservice.invovledpartyarrangements.health;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class InvolvedPartyArrangementsCheck implements HealthCheck{

	public HealthCheckResponse call() {
		HealthCheckResponseBuilder healthCheckResponse = 
					HealthCheckResponse.named("involved-party-arrangements-check")
						.withData("lastCheckDate", new Date().toString());
		return healthCheckResponse.up().build();
	}

}
