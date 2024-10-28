package com.projects.debt_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableScheduling
public class DebtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebtServiceApplication.class, args);
	}

	@Bean
    @LoadBalanced  // Add this for service discovery support
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
