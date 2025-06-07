package com.microservice.venta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceventaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceventaApplication.class, args);
	}

}
