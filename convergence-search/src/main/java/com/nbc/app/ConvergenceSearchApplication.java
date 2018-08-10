package com.nbc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConvergenceSearchApplication extends SpringBootServletInitializer {
	public static void main(String args[]) {
		 SpringApplication.run(ConvergenceSearchApplication.class, args);
	}

}