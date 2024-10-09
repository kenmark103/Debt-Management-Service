package com.projects.license_service.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "licenses")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_key", unique = true, nullable = false)
    private String licenseKey;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "assigned_to")
    private String assignedTo; // Optional field to assign the license to a user or system

    public License() {
        // Default constructor
    }

    public License(String licenseKey, LocalDateTime creationDate, LocalDateTime expirationDate, boolean isActive) {
        this.licenseKey = licenseKey;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationDate);
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "License{" +
                "id=" + id +
                ", licenseKey='" + licenseKey + '\'' +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationDate +
                ", isActive=" + isActive +
                ", assignedTo='" + assignedTo + '\'' +
                '}';
    }

}
