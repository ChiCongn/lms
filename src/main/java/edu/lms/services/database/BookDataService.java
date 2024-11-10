package edu.lms.services.database;

import edu.lms.models.book.Book;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BookDataService {
    private static final String LOAD_BOOKS_QUERY = "SELECT * FROM books";
    private static final String SEARCH_BOOK_EXIST_IN_DATABASE_QUERY = "SELECT COUNT(*) FROM books WHERE title = ?";
    private static final String ADD_BOOK_QUERY = "INSERT INTO books (title, published_year, page_count, language, description, total_copies, available_copies, authors) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM books WHERE book_id = ?";
    private static final String UPDATE_AVAILABLE_COPIES_BOOK = "UPDATE booksSET available_copies = available_copies - ? WHERE book_id = ? AND available_copies >= ?;";
    public static ObservableList<Book> loadBooksData() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_BOOKS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String authors = resultSet.getString("authors");
                String publishedYear = resultSet.getString("published_year");
                int pageCount = resultSet.getInt("page_count");
                String language = resultSet.getString("language");
                String description = resultSet.getString("description");
                BigDecimal rating = resultSet.getBigDecimal("rating");
                int totalCopies = resultSet.getInt("total_copies");
                int copiesAvailable = resultSet.getInt("available_copies");
                String coverImageUrl = resultSet.getString("cover_image_path");

                //int bookId, String title, String authors, String publishedYear, int pageCount, String language,
                //        String description, float rating, int totalCopies, int copiesAvailable, String coverImage
                Book book = new Book(id, title, authors, publishedYear, pageCount, language, description, rating, totalCopies, copiesAvailable,
                        coverImageUrl);
                bookList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error loading book data: " + e.getMessage());
        }

        return bookList;
    }

    /**
     * when successful searching book, add it into database.
     * @param book book
     */
    public static void addBook(Book book) {
        if (isExistedInDatabase(book.getTitle())) {
            return;
        }

        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getPublishedYear());
            statement.setInt(3, book.getPageCount());
            statement.setString(4, book.getLanguage());
            statement.setString(5, book.getDescription());
            statement.setInt(6, 100);
            statement.setInt(7, 100);
            statement.setString(8, book.getAuthors());

            // Execute update and get generated keys
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                book.setBookId(generatedId);
            }
        } catch (SQLException e) {
            System.err.println("Error adding book to database: " + e.getMessage());
        }
    }

    /**
     * remove a book from database.
     * @param bookId book id
     */
    public static void removeBook(int bookId) {
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_QUERY)) {

            statement.setInt(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing book from database: " + e.getMessage());
        }
    }

    public static void updateAvailableCopiesOfThisBook(int bookId, int adjustment) {
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AVAILABLE_COPIES_BOOK)) {

            statement.setInt(1, adjustment);
            statement.setInt(2, bookId);
            statement.setInt(3, adjustment); //ensure there are enough available copies

            statement.execute();
        } catch (SQLException e) {
            System.err.println("Error updating available copies of this book: " + e.getMessage());
        }
    }

    private static boolean isExistedInDatabase(String title) {
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_BOOK_EXIST_IN_DATABASE_QUERY)) {

            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error searching book from database: " + e.getMessage());
        }
        return false;
    }
}
