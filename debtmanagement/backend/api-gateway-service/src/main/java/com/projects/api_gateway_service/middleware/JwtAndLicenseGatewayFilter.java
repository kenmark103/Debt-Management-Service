package com.projects.api_gateway_service.middleware;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.projects.shared.components.JwtTokenProvider;

import reactor.core.publisher.Mono;

@Component
public class JwtAndLicenseGatewayFilter extends AbstractGatewayFilterFactory<JwtAndLicenseGatewayFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider; // Assuming you have this from auth service
    private final WebClient webClient;

    public JwtAndLicenseGatewayFilter(JwtTokenProvider jwtTokenProvider, WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.webClient = webClientBuilder.build();
    }

    // The Config class (optional) for future custom configuration
    public static class Config {
        // Add any specific configurations for this filter here (if needed)
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 1. Validate JWT Token
            String token = getJwtFromRequest(exchange);
            if (token == null || !jwtTokenProvider.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 2. Validate License Key
            String licenseKey = exchange.getRequest().getHeaders().getFirst("License-Key");
            if (licenseKey == null || !isValidLicense(licenseKey)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // If both validations pass, continue with the request
            return chain.filter(exchange);
        };
    }

    // Helper method to extract JWT token from the request headers
    private String getJwtFromRequest(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // License validation via License Service
    private boolean isValidLicense(String licenseKey) {
        // Example using WebClient to validate the license with the license service
        String licenseServiceUrl = "http://license-service/api/licenses/validate?licenseKey=" + licenseKey;

        Mono<Boolean> isValid = webClient.get()
                .uri(licenseServiceUrl)
                .retrieve()
                .bodyToMono(Boolean.class);

        // Return the license validation result
        return isValid.block(); // Blocking call, adjust based on your need
    }
}
