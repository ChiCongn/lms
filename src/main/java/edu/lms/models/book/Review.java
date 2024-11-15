package edu.lms.models.book;

public class Review {
    private int userId;
    private String username;
    private int bookId;
    private String review;
    private String avatarPath;
    private int rating;

    public Review(int userId, String username, int bookId, String review, String avatarPath, int rating) {
        this.userId = userId;
        this.username = username;
        this.bookId = bookId;
        this.review = review;
        this.avatarPath = avatarPath;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
