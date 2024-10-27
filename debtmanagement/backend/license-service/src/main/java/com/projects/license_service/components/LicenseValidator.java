package com.projects.license_service.components;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projects.license_service.exceptions.LicenseValidationException;
import com.projects.license_service.models.License;
import com.projects.license_service.repositories.LicenseRepository;
@Component
public class LicenseValidator {
    @Autowired
    private LicenseRepository licenseRepository;

    public void validateLicense(String username) throws LicenseValidationException {
        License license = licenseRepository.findByAssignedTo(username);
        if (license == null) {
            throw new LicenseValidationException("No license found for user: " + username);
        }

        LocalDateTime now = LocalDateTime.now();

        if (license.isExpired()) {
            throw new LicenseValidationException("License for user " + username + " is marked as expired");
        }

        if (license.getExpirationDate().isBefore(now)) {
            throw new LicenseValidationException("License for user " + username + " has expired on " + license.getExpirationDate());
        }

        // If we reach here, the license is valid
    }
}
