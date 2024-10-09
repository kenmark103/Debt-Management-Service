package com.projects.auth_service.dtos;

import java.util.UUID;

public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String role;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(UUID id){
        this.id=id;

    }
    public UUID getId() {
       return id;
    }
}