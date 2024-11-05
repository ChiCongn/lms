package edu.lms.services;

import java.security.SecureRandom;

public class VerificationCode {
    private static final SecureRandom random = new SecureRandom();

    public static int generateVerificationCode() {
        return 100000 + random.nextInt(900000);
    }


}
