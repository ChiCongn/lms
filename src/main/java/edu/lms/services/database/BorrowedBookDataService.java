package edu.lms.services.database;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.book.BorrowedBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BorrowedBookDataService {
    private static final String LOAD_BORROWED_BOOKS_OF_A_CLIENT_QUERY = "SELECT * FROM borrowed_books WHERE user_id = ?";
    private static final String ADD_BORROWED_BOOK_QUERY = "INSERT INTO books (user_id, book_id, borrow_date, due_date, return_date, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CHECK_IS_BORROWED_BY_THIS_CLIENT = "SELECT COUNT(*) FROM borrowed_books WHERE book_id = ? AND user_id = ?";
    private static final String COUNT_BORROWED_BOOKS_QUERY = "SELECT COUNT(*) FROM borrowed_books";
    private static final String LOAD_MONTHLY_BORROWED_BOOKS_QUERY = "SELECT MONTHNAME(borrow_date) AS month, COUNT(*) AS borrow_count " +
            "FROM borrowed_books GROUP BY MONTH(borrow_date), MONTHNAME(borrow_date) ORDER BY MONTH(borrow_date)";

    public static int getNumberOfBorrowedBook() {
        int count = 0;
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_BORROWED_BOOKS_QUERY)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error counting borrowed books: " + e.getMessage());
        }
        return count;
    }

    /**
     * load all info of borrowed books in database.
     * @return list of borrowed books
     */
    public static ObservableList<BorrowedBook> loadBorrowedBooks(int clientId) {
        ObservableList<BorrowedBook> borrowedBooks = FXCollections.observableArrayList();
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_BORROWED_BOOKS_OF_A_CLIENT_QUERY)) {

            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            //borrow_id, user_id, book_id, borrowed_date, due_date, return_date, status
            while (resultSet.next()) {
                int borrowedId = resultSet.getInt("borrow_id");
                int book_id = resultSet.getInt("book_id");
                LocalDate borrowedDate = resultSet.getDate("borrow_date").toLocalDate();
                LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
                LocalDate returnDate = null;
                Date dbReturnDate = resultSet.getDate("return_date");
                if (dbReturnDate != null) {
                    returnDate = dbReturnDate.toLocalDate();
                }
                String status = resultSet.getString("status");
                Book book = BookManager.getBook(book_id);
                BorrowedBook borrowedBook = new BorrowedBook(borrowedId, book, clientId, borrowedDate, dueDate, returnDate, status);
                borrowedBooks.add(borrowedBook);
            }

        } catch (SQLException e) {
            System.err.println("Error loading borrowed books: " + e.getMessage());
        }
        System.out.println("load borrow book of specific client");
        return borrowedBooks;
    }

    /**
     * add borrow action.
     * @param borrowedBook borrowed book
     */
    public static void addNewBorrowedBook(BorrowedBook borrowedBook) {
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BORROWED_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            //client_id, book_id, borrowed_date, due_date, return_date, status
            statement.setInt(1, borrowedBook.getClientId());
            statement.setInt(2, borrowedBook.getBook().getBookId());
            statement.setDate(3, Date.valueOf(borrowedBook.getBorrowDate()));
            statement.setDate(4, Date.valueOf(borrowedBook.getDueDate()));
            statement.setDate(5, Date.valueOf(borrowedBook.getReturnDate()));
            statement.setString(6, borrowedBook.getStatus());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            System.out.println("add a borrowed book in borrowed_books table");
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                borrowedBook.setBorrowId(generatedId);
            }
        } catch (SQLException e) {
            System.err.println("Error adding borrowed book: " + e.getMessage());
        }
    }

    public static Map<String, Integer> loadBorrowedBooksByMonth() {
        Map<String, Integer> borrowedBooksByMonth = new LinkedHashMap<>();

        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        for (String month : months) {
            borrowedBooksByMonth.put(month, 0);
        }

        System.out.println("load borrowed book by month");
        try (Connection conn = DatabaseService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(LOAD_MONTHLY_BORROWED_BOOKS_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                borrowedBooksByMonth.put(rs.getString("month"), rs.getInt("borrow_count"));
            }

        } catch (SQLException e) {
            System.err.println("Error loading borrowed books by month: " + e.getMessage());
        }

        return borrowedBooksByMonth;
    }

    private static boolean isBorrowedByThisClient(int bookId, int clientId) {
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_IS_BORROWED_BY_THIS_CLIENT)) {

            statement.setInt(1, bookId);
            statement.setInt(2, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking is borrowed by this client: " + e.getMessage());
        }
        return false;
    }
}