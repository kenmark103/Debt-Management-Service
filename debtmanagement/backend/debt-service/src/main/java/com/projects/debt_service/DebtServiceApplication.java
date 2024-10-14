package com.projects.debt_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DebtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebtServiceApplication.class, args);
	}

}
