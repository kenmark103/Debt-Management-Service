package com.projects.debt_service.clients;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.projects.auth_service.dtos.UserDto;

@Service
public class AuthServiceClient {

    private final String AUTH_SERVICE_URL = "http://auth-service/api/users/";

    private final RestTemplate restTemplate;

    @Autowired
    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUserById(UUID userId) {
        return restTemplate.getForObject(AUTH_SERVICE_URL + userId, UserDto.class);
    }
}