//package edu.lms.services.database;
//
//import edu.lms.Constants;
//import edu.lms.models.book.Book;
//import javafx.beans.Observable;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//import java.math.BigDecimal;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class Test {
//    private static final String LOAD_BOOKS_QUERY = "SELECT * FROM books";
//    private static final String SEARCH_BOOK_EXIST_IN_DATABASE_QUERY = "SELECT COUNT(*) FROM books WHERE title = ?";
//    private static final String ADD_BOOK_QUERY = "INSERT INTO books (title, authors, published_year, page_count, " +
//            "language, description, rating, total_copies, available_copies, cover_image_path, canonical_volume_link) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    private static final String DELETE_BOOK_QUERY = "DELETE FROM books WHERE book_id = ?";
//    private static final String UPDATE_AVAILABLE_COPIES_BOOK = "UPDATE booksSET available_copies = available_copies - ? WHERE book_id = ? AND available_copies >= ?;";
//    private static final String SELECT_RECOMMENDED_BOOK = "SELECT * FROM books WHERE recommended = 1";
//    private static final String SELECT_RECENTLY_ADDED_BOOKS = "SELECT * FROM books ORDER BY created_at DESC LIMIT ?";
//    private static final String SELECT_BOOKS_BY_CATEGORY =
//            "SELECT b.* FROM books b " +
//                    "JOIN bookcategories bc ON b.book_id = bc.book_id " +
//                    "WHERE bc.category_id = ?";
//
//
//    public static ObservableList<Book> getRecommendedBooks() {
//
//        try (Connection connection = DatabaseService.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SELECT_RECOMMENDED_BOOK);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                Constants.recommendedBooks.add(mapResultSetToBook(resultSet));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error retrieving recommended books: " + e.getMessage());
//        }
//        return Constants.recommendedBooks;
//    }
//
//    public static ObservableList<Book> getRecentlyAddedBooks(int limit) {
//
//        try (Connection connection = DatabaseService.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SELECT_RECENTLY_ADDED_BOOKS)) {
//
//            statement.setInt(1, limit);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    Constants.recentlyAddedBooks.add(mapResultSetToBook(resultSet));
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error retrieving recently added books: " + e.getMessage());
//        }
//        return Constants.recentlyAddedBooks;
//    }
//
//    public static ObservableList<Book> getBooksByCategory(int categoryId) {
//
//        try (Connection connection = DatabaseService.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SELECT_BOOKS_BY_CATEGORY)) {
//
//            statement.setInt(1, categoryId);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    Constants.booksByCategory.add(mapResultSetToBook(resultSet));
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error retrieving books by category: " + e.getMessage());
//        }
//        return Constants.booksByCategory;
//    }
//
//    // Helper method để map ResultSet thành đối tượng Book
//    private static Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt("book_id");
//        String title = resultSet.getString("title");
//        String authors = resultSet.getString("authors");
//        String publishedYear = resultSet.getString("published_year");
//        int pageCount = resultSet.getInt("page_count");
//        String language = resultSet.getString("language");
//        String description = resultSet.getString("description");
//        BigDecimal rating = resultSet.getBigDecimal("rating");
//        BigDecimal price = resultSet.getBigDecimal("price");
//        int totalCopies = resultSet.getInt("total_copies");
//        int copiesAvailable = resultSet.getInt("available_copies");
//        String coverImage = resultSet.getString("cover_image_path");
//        String canonicalVolumeLink = resultSet.getString("canonical_volume_link");
//
//        return new Book(id, title, authors, publishedYear, pageCount, language, description, rating, price,
//                totalCopies, copiesAvailable, coverImage, canonicalVolumeLink);
//    }
//
//
//    public static void removeBook(int bookId) {
//        try (Connection connection = DatabaseService.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_QUERY)) {
//
//            statement.setInt(1, bookId);
//            statement.executeUpdate();
//            System.out.println("remove a book from database");
//        } catch (SQLException e) {
//            System.err.println("Error removing book from database: " + e.getMessage());
//        }
//    }
//
//    public static void updateAvailableCopiesOfThisBook(int bookId, int adjustment) {
//        try (Connection connection = DatabaseService.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE_AVAILABLE_COPIES_BOOK)) {
//
//            statement.setInt(1, adjustment);
//            statement.setInt(2, bookId);
//            statement.setInt(3, adjustment); //ensure there are enough available copies
//
//            statement.execute();
//            System.out.println("update available copies of book");
//        } catch (SQLException e) {
//            System.err.println("Error updating available copies of this book: " + e.getMessage());
//        }
//    }
//
//    private static boolean isExistedInDatabase(String title) {
//        try (Connection connection = DatabaseService.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SEARCH_BOOK_EXIST_IN_DATABASE_QUERY)) {
//
//            statement.setString(1, title);
//
//            ResultSet resultSet = statement.executeQuery();
//            System.out.println("check this book is existed in database");
//            if (resultSet.next()) {
//                return resultSet.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            System.err.println("Error searching book from database: " + e.getMessage());
//        }
//        return false;
//    }
//}
