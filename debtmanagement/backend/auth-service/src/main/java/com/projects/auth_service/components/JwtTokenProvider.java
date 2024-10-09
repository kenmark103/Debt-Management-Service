package com.projects.auth_service.components;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretString;

    @Value("${jwt.expiration}")
    private final Date expirationTime;

    private final SecretKey secretKey;

    public JwtTokenProvider(){
        long expirationTimeInMs = 3600000; // Set sexpiration to 1 hour
        this.expirationTime = new Date(System.currentTimeMillis() + expirationTimeInMs);
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
    }

    // Generate JWT token
    public String generateToken(String username, String role) {
        return Jwts.builder()
        .claim("Role", role)
        .subject(username)
        .signWith(secretKey)
        .expiration(expirationTime) //a java.util.Date
        .issuedAt(new Date())
        .compact();
    }
    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseEncryptedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Extract username from token
    public String getUsernameFromToken(String token) {
        Jws<Claims>parsed = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token);
        String username = parsed.getPayload().getSubject();
        return username;
    }

    // Extract role from token
    public String getRoleFromToken(String token) {
        Jws<Claims>parsed = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token);
        String role = (String)parsed.getPayload().get("Role");
        return role;
    }
}
