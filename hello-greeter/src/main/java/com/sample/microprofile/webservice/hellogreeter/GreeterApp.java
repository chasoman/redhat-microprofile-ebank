package com.sample.microprofile.webservice.hellogreeter;

//TODO: Add a JAX-RS ApplicationPath annotation
//TODO: Extend the Java WebService Application class
public class GreeterApp {
	
	public static void main(String args[]) {
		Greeter greeter = new Greeter();
		System.out.println(greeter.greet(args[0]));
	}
}
