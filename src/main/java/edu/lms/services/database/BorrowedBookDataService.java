package edu.lms.services.database;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.Client;
import edu.lms.models.user.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;


public class BorrowedBookDataService {
    private static final String LOAD_BORROWED_BOOKS_OF_A_CLIENT_QUERY = "SELECT * FROM borrowedbooks WHERE client_id = ?";
    private static final String ADD_BORROWED_BOOK_QUERY = "INSERT INTO books (client_id, book_id, borrowed_date, due_date, return_date, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CHECK_IS_BORROWED_BY_THIS_CLIENT = "SELECT COUNT(*) FROM borrowedbooks WHERE book_id = ? AND client_id = ?";

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
            //borrow_id, client_id, book_id, borrowed_date, due_date, return_date, status
            while (resultSet.next()) {
                int borrowedId = resultSet.getInt("borrow_id");
                int book_id = resultSet.getInt("book_id");
                LocalDate borrowedDate = resultSet.getDate("borrowed_date").toLocalDate();
                LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
                LocalDate returnDate = resultSet.getDate("return_date").toLocalDate();
                String status = resultSet.getString("status");
                Book book = BookManager.getBook(book_id);
                Client client = UserManager.getClient(clientId);
                BorrowedBook borrowedBook = new BorrowedBook(borrowedId, book, client, borrowedDate, dueDate, returnDate, status);
                borrowedBooks.add(borrowedBook);
            }

        } catch (SQLException e) {
            System.err.println("Error loading borrowed books: " + e.getMessage());
        }
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
            statement.setInt(1, borrowedBook.getClient().getId());
            statement.setInt(2, borrowedBook.getBook().getBookId());
            statement.setDate(3, Date.valueOf(borrowedBook.getBorrowDate()));
            statement.setDate(4, Date.valueOf(borrowedBook.getDueDate()));
            statement.setDate(5, Date.valueOf(borrowedBook.getReturnDate()));
            statement.setString(6, borrowedBook.getStatus());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                borrowedBook.setBorrow_id(generatedId);
            }
        } catch (SQLException e) {
            System.err.println("Error adding borrowed book: " + e.getMessage());
        }
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
