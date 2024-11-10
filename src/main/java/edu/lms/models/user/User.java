package edu.lms.models.user;

public abstract class User {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String avatarPath;
    protected Gender gender = Gender.OTHER;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(int id, String username, String password, String email,
                String avatarPath, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatarPath = avatarPath;
        this.gender = gender;
    }

    public User(String username, String password, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }

    public void changePassword(String newPassword) {
        password = newPassword;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
