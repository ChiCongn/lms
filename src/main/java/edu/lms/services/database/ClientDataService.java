package edu.lms.services.database;

import edu.lms.models.user.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDataService {
    private static final String LOAD_CLIENTS_QUERY = "SELECT * FROM clients";
    private static final String LOAD_BORROWED_BOOKS_QUERY = "SELECT bookID FROM borrowed_books WHERE clientID = ?";
    private static final String ADD_BORROWED_BOOK_QUERY = "INSERT INTO borrowed_books (clientID, bookID) VALUES (?, ?)";
    private static final String REMOVE_BORROWED_BOOK_QUERY = "DELETE FROM borrowed_books WHERE clientID = ? AND bookID = ?";
    private static final String MARK_BOOK_AS_OVERDUE_QUERY = "UPDATE borrowed_books SET overdue = TRUE WHERE clientID = ? AND bookID = ?";

    private static DatabaseService dbService;

    public ClientDataService() {
        dbService = DatabaseService.getInstance();
    }

    /**
     * load all list of clients form database.
     * @return list of clients
     */
    public static List<Client> loadClientsData() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(LOAD_CLIENTS_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int clientID = rs.getInt("clientID");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String avatarUrl = rs.getString("avatarUrl");

                Client client = new Client(clientID, username, password, avatarUrl);
                client.setBorrowedBooks(loadBorrowedBooks(clientID));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
        return clients;
    }

    /**
     * load list of borrowed book ids for each client.
     * @param clientID id of client
     * @return list of borrowed book id for each client
     */
    private static List<Integer> loadBorrowedBooks(int clientID) {
        List<Integer> borrowedBooks = new ArrayList<>();
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(LOAD_BORROWED_BOOKS_QUERY)) {

            stmt.setInt(1, clientID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int bookID = rs.getInt("bookID");
                    borrowedBooks.add(bookID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading borrowed books: " + e.getMessage());
        }
        return borrowedBooks;
    }

    /**
     * add a new borrowed book for a client.
     *
     * @param clientID client id
     * @param bookID   book id
     */
    public static void borrowBook(int clientID, int bookID) {
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(ADD_BORROWED_BOOK_QUERY)) {

            stmt.setInt(1, clientID);
            stmt.setInt(2, bookID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error borrowing book: " + e.getMessage());
        }
    }

    /**
     * remove a borrowed book.
     *
     * @param clientID client id
     * @param bookID   book id
     */
    public static void returnBook(int clientID, int bookID) {
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(REMOVE_BORROWED_BOOK_QUERY)) {

            stmt.setInt(1, clientID);
            stmt.setInt(2, bookID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
    }

    /**
     * mark a book as overdue.
     * @param clientID client id
     * @param bookID book id
     * @return true if success and vice versa
     */
    public static boolean markBookAsOverdue(int clientID, int bookID) {
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(MARK_BOOK_AS_OVERDUE_QUERY)) {

            stmt.setInt(1, clientID);
            stmt.setInt(2, bookID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error marking book as overdue: " + e.getMessage());
        }
        return false;
    }
}