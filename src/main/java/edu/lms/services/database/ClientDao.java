package edu.lms.services.database;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ClientDao {
    private static final String LOAD_FAVOURITE_BOOKS_QUERY = "SELECT * FROM favourite_book WHERE user_id = ?";

    private static final String ADD_FAVOURITE_BOOK_QUERY = "INSERT INTO favourite_book (user_id, book_id) WHERE user_id = ?";

    private static final String UNFAVOURITE_BOOK_QUERY = "DELETE FROM favourite_book WHERE user_id = ? AND book_id = ?";

    private static final String LOAD_RECENT_BOOKS_QUERY = "SELECT * FROM recent_book WHERE user_id = ? ORDER BY accessed_at DESC LIMIT ?";

    private static final String ADD_RECENT_BOOK = "INSERT INTO recent_book (user_id, book_id, accessed_at) VALUES (?, ?, ?)";

    private static final String UPDATE_ACCESS_TIME = "UPDATE recent_books SET accessed_at = ? WHERE user_id = ? AND book_id = ?";

    private static final String DELETE_RECENT_BOOK = "DELETE FROM recent_book WHERE user_id = ? AND book_id = ?";


    public static boolean addFavouriteBook(int userId, int bookId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_FAVOURITE_BOOK_QUERY)) {

            statement.setInt(1, userId);
            statement.setInt(2, bookId);

            System.out.println("add favourite book");
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding book to favorites: " + e.getMessage());
        }
        return false;
    }

    public static ObservableList<Book> loadFavouriteBooks(int userId) {
        ObservableList<Book> favouriteBooks = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_FAVOURITE_BOOKS_QUERY)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    favouriteBooks.add(BookManager.getBook(bookId));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading favorite books: " + e.getMessage());
        }

        return favouriteBooks;
    }

    public static boolean unfavouriteBook(int userId, int bookId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UNFAVOURITE_BOOK_QUERY)) {

            statement.setInt(1, userId);
            statement.setInt(2, bookId);

            System.out.println("unfavourite book");
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error while removing book from favorites: " + e.getMessage());
        }
        return false;
    }

    /**
     * -----------------------------------------
     * Recent Books
     * -----------------------------------------
     */
    public static ObservableList<Book> loadRecentBooks(int userId, int limit) {
        ObservableList<Book> recentBooks = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_RECENT_BOOKS_QUERY)) {

            statement.setInt(1, userId);
            statement.setInt(2, limit);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    recentBooks.add(BookManager.getBook(bookId));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading recent books: " + e.getMessage());
        }

        return recentBooks;
    }

    public static boolean addRecentBook(int userId, int bookId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_RECENT_BOOK)) {

            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding recent book: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateAccessTime(int userId, int bookId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ACCESS_TIME)) {

            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setInt(2, userId);
            statement.setInt(3, bookId);

            System.out.println("update access time");
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating access time: " + e.getMessage());
        }
        return false;
    }


    public static boolean deleteRecentBook(int userId, int bookId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECENT_BOOK)) {

            statement.setInt(1, userId);
            statement.setInt(2, bookId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting recent book: " + e.getMessage());
        }
        return false;
    }

}
