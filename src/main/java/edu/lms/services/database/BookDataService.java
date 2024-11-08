package edu.lms.services.database;

import edu.lms.models.book.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BookDataService {
    private static final String LOAD_BOOKS_QUERY = "SELECT id, title, author, isbn, publishedYear, coverImageUrl FROM books";
    private static final String ADD_BOOK_QUERY = "INSERT INTO books (title, published_year, page_count, language, description, total_copies, copies_available) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM books WHERE id = ?";
    public static List<Book> loadBooksData() {
        List<Book> bookList = new ArrayList<>();

        try (Connection conn = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(LOAD_BOOKS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String publishedYear = resultSet.getString("publishedYear");
                int pageCount = resultSet.getInt("pageCount");
                String language = resultSet.getString("language");
                String description = resultSet.getString("description");
                BigDecimal rating = resultSet.getBigDecimal("rating");
                int totalCopies = resultSet.getInt("total_copies");
                int copiesAvailable = resultSet.getInt("copies_available");
                String coverImageUrl = resultSet.getString("cover_image_path");

                //int bookId, String title, String publishedYear, int pageCount, String language,
                //        String description, float rating, int totalCopies, int copiesAvailable, String coverImage
                Book book = new Book(id, title, publishedYear, pageCount, language, description, rating, totalCopies, copiesAvailable,
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
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getDescription());
            statement.setString(3, book.getPublishedYear());
            statement.setString(4, book.getCoverImage());

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getPublishedYear());
            statement.setInt(3, book.getPageCount());
            statement.setString(4, book.getLanguage());
            statement.setString(5, book.getDescription());
            statement.setInt(6, 100);
            statement.setInt(7, 100);
            //statement.setString(6, book.getCoverImage());

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
}
