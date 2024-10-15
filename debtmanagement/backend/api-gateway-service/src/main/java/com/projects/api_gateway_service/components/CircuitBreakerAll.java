package com.projects.api_gateway_service.components;

import java.time.Duration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class CircuitBreakerAll {

    public static void main(String[] args) {
        // Create CircuitBreaker configuration
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50) // If 50% of requests fail, the circuit breaker will open
            .waitDurationInOpenState(Duration.ofSeconds(30)) // Wait 30 seconds before trying again
            .slidingWindowSize(10) // Look at the last 10 calls to evaluate failure rate
            .build();

        // Create a CircuitBreaker registry
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

        // Get a CircuitBreaker from the registry
        CircuitBreaker circuitBreaker = registry.circuitBreaker("authServiceCircuit");

        // Example: Using the circuit breaker to make an API call
        Runnable decoratedApiCall = CircuitBreaker.decorateRunnable(circuitBreaker, () -> {
            // Call to auth-service or other microservice
            System.out.println("Calling the service...");
            // Simulate a failure or success
        });

        try {
            // Execute the decorated call
            decoratedApiCall.run();
        } catch (Exception e) {
            System.out.println("Circuit breaker opened! Fallback triggered.");
        }
    }
}