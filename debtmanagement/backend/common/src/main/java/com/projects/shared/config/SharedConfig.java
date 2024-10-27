package com.projects.shared.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.projects.shared.components.JwtTokenProvider;

@Configuration
public class SharedConfig {

    @Bean
    @Autowired
    public JwtTokenProvider jwtTokenProvider(
            @Value("${jwt.secret}") String secretString,
            @Value("${jwt.expiration}") long expirationTimeInMs) {
        return new JwtTokenProvider(secretString, expirationTimeInMs);
    }
}