package com.projects.api_gateway_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.projects.api_gateway_service.middleware.JwtAuthenticationFilter;

@Configuration
public class RouteConfig {

    @Autowired
    private JwtAuthenticationFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Auth Service Routes
            .route("auth-service", r -> r
                .path("/auth/**")
                .uri("lb://auth-service"))

             // Debt Service Routes - JWT Required
             .route("debt-service", r -> r
             .path("/api/debts/**")
             .filters(f -> f.filter(authFilter.apply(new JwtAuthenticationFilter.Config())))
             .uri("lb://debt-service"))
         
            
            // Other Service Routes (with JWT filter)
            .route("protected-service", r -> r
                .path("/api/**")
                .filters(f -> f.filter(authFilter.apply(new JwtAuthenticationFilter.Config())))
                .uri("lb://your-service-name"))
            .build();
    }
}