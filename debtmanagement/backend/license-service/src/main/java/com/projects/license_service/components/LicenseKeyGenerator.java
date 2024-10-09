package com.projects.license_service.components;

import java.security.SecureRandom;
import java.util.Base64;

public class LicenseKeyGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final int KEY_LENGTH = 32;

    public String generateLicenseKey(){
        byte[] key = new byte[KEY_LENGTH];
        random.nextBytes(key);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(key);
    }

}
