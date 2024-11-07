package edu.lms.models.user;

public abstract class User {
    protected String username;
    protected String password;
    protected int ID;
    protected String avatar;

    public User(int ID, String username, String password, String avatar) {
        this.username = username;
        this.password = password;
        this.ID = ID;
        this.avatar = avatar;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
