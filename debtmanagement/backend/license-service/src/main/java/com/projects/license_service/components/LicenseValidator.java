package com.projects.license_service.components;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.projects.license_service.models.License;
import com.projects.license_service.repositories.LicenseRepository;

public class LicenseValidator {
     @Autowired
    private LicenseRepository licenseRepository;

    public boolean isValidLicense(String licenseKey) {
        License license = licenseRepository.findByLicenseKey(licenseKey);
        if (license == null) {
            return false;
        }

        // Check if the license is expired
        return !license.isExpired() && license.getExpirationDate().isAfter(LocalDateTime.now());
    }
}
