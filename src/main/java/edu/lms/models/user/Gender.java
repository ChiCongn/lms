package edu.lms.models.user;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static String takeGender(Gender gender) {
        if (gender == null) {
            return "other";
        } else if (gender == MALE) {
            return "male";
        } else if (gender == FEMALE) {
            return "female";
        } else {
            return "other";
        }
    }
}
