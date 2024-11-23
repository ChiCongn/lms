package edu.lms.models.review;

import edu.lms.models.user.UserManager;

import java.util.Objects;

public class Review {
    private int userId;
    private int bookId;
    private String review;
    private String avatarPath;
    private int rating;

    public Review(int userId, int bookId, String review, int rating) {
        this.userId = userId;
        this.bookId = bookId;
        this.review = review;
        this.avatarPath = Objects.requireNonNull(UserManager.getClient(userId)).getAvatarPath();
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
