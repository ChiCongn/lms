package edu.lms.services.database;

import edu.lms.models.review.Review;
import edu.lms.models.review.ReviewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDao {
    private static final String LOAD_REVIEWS_OF_SPECIFIC_BOOK_QUERY = "SELECT user_id, review_text, rating FROM reviews WHERE book_id = ?";
    private static final String ADD_REVIEW_QUERY = "INSERT INTO reviews (user_id, book_id, review_text, rating) VALUE (?, ?, ?, ?)";
    private static final String CHECK_REVIEW_EXISTS_QUERY  = "SELECT COUNT(*) FROM reviews WHERE user_id = ? AND book_id = ?";
    private static final String ALERT_REVIEW_QUERY = "UPDATE reviews SET review_text = ? WHERE user_id = ? AND book_id = ?";


    public static void addReview(Review review) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_REVIEW_QUERY)) {

            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getBookId());
            statement.setString(3, review.getReview());
            statement.setInt(4, review.getRating());

            System.out.println("add review");
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding review: " + e.getMessage());
        }
    }

    public static ObservableList<Review> loadReviewsOfSpecificBook(int bookId) {
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_REVIEWS_OF_SPECIFIC_BOOK_QUERY)) {

            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String reviewText = resultSet.getString("review_text");
                int rating = resultSet.getInt("rating");
                Review loadReview = new Review(userId, bookId, reviewText, rating);
                reviews.add(loadReview);
            }
        } catch (SQLException e) {
            System.err.println("Error loading reviews of specific book: " + e.getMessage());
        }
        System.out.println("load reviews of specific book");
        return reviews;
    }

    public static boolean hasUserVoted(int userId, int bookId) {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CHECK_REVIEW_EXISTS_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user has voted: " + e.getMessage());
        }
        return false;
    }

    public static boolean alertReview(Review review) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ALERT_REVIEW_QUERY)) {

            statement.setString(1, review.getReview());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getBookId());

            int rowsUpdated = statement.executeUpdate();
            System.out.println("alert review ");
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error alerting review" + e.getMessage());
        }
        return false;
    }
}
