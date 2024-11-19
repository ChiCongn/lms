package edu.lms.models.user;

public class Admin extends User {
    public Admin(String username, String password, String email) {
        super(username, password, email);
    }

    public Admin(int id, String username, String password, String email, String avatarPath, String status, Gender gender) {
        super(id, username, password, email, avatarPath, status, gender);
    }

    public Admin(String username, String password, String email, Gender gender) {
        super(username, password, email, gender);
    }
}
