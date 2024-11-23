package edu.lms.services.database;

import edu.lms.models.review.Review;
import edu.lms.models.user.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ReviewDataService {
    private static final String LOAD_REVIEWS_OF_SPECIFIC_BOOK_QUERY = "SELECT user_id, review_text, rating FROM reviews WHERE book_id = ?";
    private static final String ADD_REVIEW_QUERY = "INSERT INTO reviews (user_id, book_id, review_text, rating) VALUE (?, ?, ?, ?)";

    public static void addReview(Review review) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_REVIEW_QUERY)) {

            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getBookId());
            statement.setString(3, review.getReview());
            statement.setInt(4, review.getRating());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding review: " + e.getMessage());
        }
    }

    public static ObservableList<Review> loadReviewsOfSpecificBook(int bookId) {
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_REVIEWS_OF_SPECIFIC_BOOK_QUERY)) {

            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = Objects.requireNonNull(UserManager.getClient(userId)).getUsername();
                String avatarPath = Objects.requireNonNull(UserManager.getClient(userId)).getUsername();
                String reviewText = resultSet.getString("review_text");
                int rating = resultSet.getInt("rating");
                Review loadReview = new Review(userId, username, bookId, reviewText, avatarPath, rating);
                reviews.add(loadReview);
            }
        } catch (SQLException e) {
            System.err.println("Error loading reviews of specific book: " + e.getMessage());
        }
        System.out.println("load reviews of specific book");
        return reviews;
    }
}
