package edu.lms.models.user;

public class Librarian extends User {
    public Librarian(String username, String password, String email) {
        super(username, password, email);
    }

    public Librarian(int id, String username, String password, String email, String avatarPath, String status, Gender gender) {
        super(id, username, password, email, avatarPath, status, gender);
    }

    public Librarian(String username, String password, String email, Gender gender) {
        super(username, password, email, gender);
    }
}
