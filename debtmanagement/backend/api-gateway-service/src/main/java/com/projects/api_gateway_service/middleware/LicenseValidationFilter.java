package com.projects.api_gateway_service.middleware;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LicenseValidationFilter extends AbstractGatewayFilterFactory<LicenseValidationFilter.Config> {

    public LicenseValidationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String licenseKey = exchange.getRequest().getHeaders().getFirst("License-Key");

            if (!isValidLicense(licenseKey)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    private boolean isValidLicense(String licenseKey) {
        // Add logic to validate license key
        return licenseKey != null && licenseKey.equals("valid-license-key");
    }

    public static class Config {
        // Configuration properties can be added here
    }
}