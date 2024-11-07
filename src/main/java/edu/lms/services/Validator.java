package edu.lms.services;

import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean checkValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }
}
