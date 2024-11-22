package edu.lms.services.database;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class BookDataService {
    private static final String LOAD_BOOKS_QUERY = "SELECT * FROM books";
    private static final String SEARCH_BOOK_EXIST_IN_DATABASE_QUERY = "SELECT COUNT(*) FROM books WHERE title = ?";
    private static final String ADD_BOOK_QUERY = "INSERT INTO books (title, authors, published_year, page_count, " +
            "language, description, rating, total_copies, available_copies, cover_image_path, canonical_volume_link, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM books WHERE book_id = ?";
    private static final String UPDATE_AVAILABLE_COPIES_BOOK_QUERY = "UPDATE books SET available_copies = available_copies - ? WHERE book_id = ? AND available_copies >= ?;";
    private static final String SET_AVAILABLE_COPIES_QUERY = "UPDATE books SET available_copies = ? WHERE book_id = ?";
    private static final String SET_TOTAL_COPIES_QUERY = "UPDATE books SET total_copies = ? WHERE book_id = ?";
    private static final String SET_PRICE_QUERY = "UPDATE books SET price = ? WHERE book_id = ?";
    private static final String LOAD_TOP_CHOICES_QUERY = "SELECT book_id, (total_copies - available_copies) AS times FROM books ORDER BY times DESC LIMIT 10";
    private static final String INSERT_CATEGORY_QUERY = "INSERT INTO categories (name) VALUES (?)";
    private static final String INSERT_BOOK_CATEGORIES_QUERY = "INSERT INTO book_categories (book_id, category_id) VALUES (?, ?)";
    private static final String GET_CATEGORIES_FOR_BOOK_QUERY = "SELECT c.name FROM categories c "
            + "JOIN book_categories bc ON c.category_id = bc.category_id WHERE bc.book_id = ?";
    private static final String GET_CATEGORY_ID_QUERY = "SELECT category_id FROM categories WHERE name = ?";
    private static final String LOAD_CATEGORY_DISTRIBUTION_QUERY = "SELECT bc.category_id, c.name, COUNT(bc.book_id) AS count FROM book_categories bc " +
            "JOIN categories c on c.category_id = bc.category_id group by bc.category_id, c.name";

    public static ObservableList<Book> loadBooksData() {
        // liên kết api ?
        ObservableList<Book> bookList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             // hàm bắt buộc
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
                BigDecimal price = resultSet.getBigDecimal("price");
                int totalCopies = resultSet.getInt("total_copies");
                int copiesAvailable = resultSet.getInt("available_copies");
                String coverImageUrl = resultSet.getString("cover_image_path");
                String canonicalVolumeLink = resultSet.getString("canonical_volume_link");

                //int bookId, String title, String authors, String publishedYear, int pageCount, String language,
                //String description, BigDecimal rating, int totalCopies, int availableCopies, String coverImage, String canonicalVolumeLink
                Book book = new Book(id, title, authors, publishedYear, pageCount, language, description, rating, price, totalCopies, copiesAvailable,
                        coverImageUrl, canonicalVolumeLink);
                book.setCategories(getCategoriesForBook(id));
                bookList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error loading book data: " + e.getMessage());
        }
        System.out.println("load all book success!");
        return bookList;
    }

    /**
     * when successful searching book, add it into database.
     * @param book book
     */
    public static boolean addBook(Book book) {
        if (isExistedInDatabase(book.getTitle())) {
            return false;
        }

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            //title, authors, published_year, pageCount, language, description, rating,
            // total_copies, available_copies, coverImageUrl, canonicalVolumeLink
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthors());
            statement.setString(3, book.getPublishedYear());
            statement.setInt(4, book.getPageCount());
            statement.setString(5, book.getLanguage());
            statement.setString(6, book.getDescription());
            statement.setBigDecimal(7, book.getRating());
            statement.setInt(8, book.getTotalCopies());
            statement.setInt(9, book.getAvailableCopies());
            statement.setString(10, book.getCoverImage());
            statement.setString(11, book.getCanonicalVolumeLink());
            statement.setBigDecimal(12, book.getPrice());

            // Execute update and get generated keys
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            System.out.println("add a new book into database");
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                book.setBookId(generatedId);
                insertBookCategories(generatedId, book.getCategories());
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding book to database: " + e.getMessage());
        }
        return false;
    }

    /**
     * remove a book from database.
     * @param bookId book id
     */
    public static boolean removeBook(int bookId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_QUERY)) {

            statement.setInt(1, bookId);
            statement.executeUpdate();
            System.out.println("remove book has id: " + bookId + "from database");
            return true;
        } catch (SQLException e) {
            System.err.println("Error removing book from database: " + e.getMessage());
        }
        return false;
    }

    public static Map<String, Integer> loadCategoryDistributionData() {
        Map<String, Integer> categoryData = new LinkedHashMap<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(LOAD_CATEGORY_DISTRIBUTION_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("load category distribution");
            while (rs.next()) {
                categoryData.put(rs.getString("name"), rs.getInt("count"));
            }

        } catch (SQLException e) {
            System.err.println("Error loading category distribution data: " + e.getMessage());
        }

        return categoryData;
    }

    public static boolean updateAvailableCopiesOfThisBook(int bookId, int adjustment) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AVAILABLE_COPIES_BOOK_QUERY)) {

            statement.setInt(1, adjustment);
            statement.setInt(2, bookId);
            statement.setInt(3, adjustment); //ensure there are enough available copies

            statement.execute();
            System.out.println("update available copies of book");
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating available copies of this book: " + e.getMessage());
        }
        return false;
    }

    public static boolean setAvailableCopiesOfSpecificBook(int bookId, int newAvailableCopies) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_AVAILABLE_COPIES_QUERY)) {

            statement.setInt(1, newAvailableCopies);
            statement.setInt(2, bookId);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("set available copies of this book has id: " + bookId);
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error setting available copies of this book: " + e.getMessage());
        }
        return false;
    }

    public static boolean setTotalCopiesOfSpecificBook(int bookId, int newTotalCopies) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_TOTAL_COPIES_QUERY)) {

            statement.setInt(1, newTotalCopies);
            statement.setInt(2, bookId);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("set total copies of this book has id: " + bookId);
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error setting total copies of this book: " + e.getMessage());
        }
        return false;
    }

    public static boolean setPriceOfSpecificBook(int bookId, BigDecimal newPrice) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_PRICE_QUERY)) {

            statement.setBigDecimal(1, newPrice);
            statement.setInt(2, bookId);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("set total copies of this book has id: " + bookId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error setting price for this book: " + bookId);
        }
        return false;
    }

    public static List<Book> loadTopChoicesBook() {
        List<Book> topChoiceBooks = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_TOP_CHOICES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                Book book = BookManager.getBook(bookId);
                book.initializeThumbnail();
                topChoiceBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error loading top choices book: " + e.getMessage());
        }
        return topChoiceBooks;
    }

    private static boolean isExistedInDatabase(String title) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_BOOK_EXIST_IN_DATABASE_QUERY)) {

            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();
            System.out.println("check book with title: " + title + "is existed in database");
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error searching book from database: " + e.getMessage());
        }
        return false;
    }

    private static void insertBookCategories(int bookId, String categories) {
        if (categories == null) {
            System.out.println("categories is null");
            return;
        }

        System.out.println("insert book categories");
        String[] categoryArray = categories.split(",\\s*");
        for (String category : categoryArray) {
            int categoryId = insertCategoryIfNotExists(category);
            if (categoryId == -1) {
                System.err.println("Error: something is wrong when insert into book_categories. Can not find or add categories_id");
                return;
            }
            insertBookCategoryRelation(bookId, categoryId);
        }
    }

    private static void insertBookCategoryRelation(int bookId, int categoryId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_BOOK_CATEGORIES_QUERY)) {

            insertStmt.setInt(1, bookId);
            insertStmt.setInt(2, categoryId);
            insertStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertCategoryIfNotExists(String categoryName) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(GET_CATEGORY_ID_QUERY)) {
                checkStmt.setString(1, categoryName);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("category_id");
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(INSERT_CATEGORY_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                        insertStmt.setString(1, categoryName);
                        insertStmt.executeUpdate();
                        try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                return generatedKeys.getInt(1);
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting category: " + e.getMessage());
        }

        return -1;
    }

    private static String getCategoriesForBook(int bookId) {
        StringBuilder categories = new StringBuilder();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CATEGORIES_FOR_BOOK_QUERY)) {

            statement.setInt(1, bookId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                categories.append(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting categories for book: " + e.getMessage());
        }
        return categories.toString();
    }

}
