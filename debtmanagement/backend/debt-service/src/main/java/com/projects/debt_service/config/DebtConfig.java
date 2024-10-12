package com.projects.debt_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebtConfig {

    // Add any beans or configurations related to Debt service here
    @Bean
    public String debtConfigBean() {
        return "Debt Service Configuration";
    }
}