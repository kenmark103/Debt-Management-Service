package com.projects.shared.components;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long expirationTimeInMs;

    public JwtTokenProvider(String secretString, long expirationTimeInMs) {
        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes());
        this.expirationTimeInMs = expirationTimeInMs;
    }

    // Generate JWT token
    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeInMs);

        return Jwts.builder()
            .claim("Role", role)
            .subject(username)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            
            // Check if the token has expired
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract username from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return claims.getSubject();
    }

    // Extract role from token
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return claims.get("Role", String.class);
    }
}
