package com.sample.microprofile.webservice.hellogreeter;

//TODO: Inject the JAX-RS Path annotation
public class Greeter {
	
	//TODO: Inject the JAX-RS GET annotation to designate this as a GET operation
	//TODO: Inject the JAX-RS Producer annotation to denote a plain text response 
	//TODO: Inject the JAX-RS Path annotation to accept the personName path parameter
	public String greet(String personName) {
		
		//TODO: Transform the return value into 
		// a Java WebService Response.
		return "!!!Hello "
				+ personName
				+ ". Welcome to the world of Java!!!";
	}
}
