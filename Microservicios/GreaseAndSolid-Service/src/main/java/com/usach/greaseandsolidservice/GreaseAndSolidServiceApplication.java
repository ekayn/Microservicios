package com.usach.greaseandsolidservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GreaseAndSolidServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreaseAndSolidServiceApplication.class, args);
	}

}
