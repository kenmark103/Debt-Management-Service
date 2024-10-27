package com.projects.api_gateway_service.middleware;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.projects.shared.components.JwtTokenProvider;
import com.projects.shared.config.SharedConfig;

import reactor.core.publisher.Mono;

@Component
@Import(SharedConfig.class)
public class JwtAndLicenseGatewayFilter extends AbstractGatewayFilterFactory<JwtAndLicenseGatewayFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider; // Assuming you have this from auth service
    private final WebClient webClient;
    
    private final List<String> publicPaths = Arrays.asList("/api/auth/register", "/api/auth/login", "/api/license/purchase", "/eureka/**");
    private final List<String> authOnlyPaths = Arrays.asList("/api/license/status", "/api/user/profile");


    @Autowired
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
            String path = exchange.getRequest().getURI().getPath();
            
            if (isPublicPath(path)) {
                return chain.filter(exchange);
            }

            return validateJwt(exchange)
                .flatMap(username -> {
                    if (isAuthOnlyPath(path)) {
                        return chain.filter(exchange);
                    }
                    return validateLicense(username).then();
                })
                .then(chain.filter(exchange))
                .onErrorResume(UnauthorizedException.class, e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().writeWith(
                        Mono.just(exchange.getResponse().bufferFactory().wrap(e.getMessage().getBytes()))
                    );
                });
        };
    }

    private boolean isPublicPath(String path) {
        return publicPaths.stream().anyMatch(path::startsWith);
    }

    private boolean isAuthOnlyPath(String path) {
        return authOnlyPaths.stream().anyMatch(path::startsWith);
    }

    private Mono<String> validateJwt(ServerWebExchange exchange) {
        String token = getJwtFromRequest(exchange);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return Mono.error(new UnauthorizedException("Invalid or missing JWT token"));
        }
        return Mono.just(jwtTokenProvider.getUsernameFromToken(token));
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
    private Mono<Boolean> validateLicense(String username) {
        String licenseServiceUrl = "http://license-service/api/licenses/validate?username=" + username;
        return webClient.get()
                .uri(licenseServiceUrl)
                .retrieve()
                .bodyToMono(Boolean.class)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new UnauthorizedException("Invalid license for user"));
                    }
                    return Mono.just(true);
                });
    }

    private static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

}
