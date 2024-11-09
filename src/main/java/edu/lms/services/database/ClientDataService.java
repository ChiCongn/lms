package edu.lms.services.database;

import edu.lms.models.user.Client;
import edu.lms.models.user.Gender;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDataService {
    private static final String LOAD_CLIENTS_QUERY = "SELECT * FROM clients";
    private static final String LOAD_SINGLE_CLIENT_QUERY = "SELECT * FROM clients WHERE client_id = ?";
    private static final String LOAD_BORROWED_BOOKS_QUERY = "SELECT book_id FROM borrowedbooks WHERE client_id = ?";
    private static final String ADD_BORROWED_BOOK_QUERY = "INSERT INTO borrowedbooks (client_id, book_id) VALUES (?, ?)";
    private static final String REMOVE_BORROWED_BOOK_QUERY = "DELETE FROM borrowedbooks WHERE client_id = ? AND book_id = ?";
    private static final String MARK_BOOK_AS_OVERDUE_QUERY = "UPDATE borrowedbooks SET status = 'overdue' WHERE client_id = ? AND book_id = ?";

    private static DatabaseService dbService;

    public ClientDataService() {
        dbService = DatabaseService.getInstance();
    }

    /**
     * load all list of clients form database.
     * @return list of clients
     */
    public static ObservableList<Client> loadClientsData() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(LOAD_CLIENTS_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int clientId = rs.getInt("client_id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String avatarPath = rs.getString("avatar_path");
                String genderString = rs.getString("gender");
                Gender gender = Gender.valueOf(genderString.toUpperCase());

                //int id, String username, String password, String email, String avatarPath, Gender gender
                Client client = new Client(clientId, email, username, password, avatarPath, gender);
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
        return clients;
    }

    public static Client loadClientsData(int clientId) {
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(LOAD_SINGLE_CLIENT_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String avatarPath = rs.getString("avatar_path");
                String genderString = rs.getString("gender");
                Gender gender = Gender.valueOf(genderString.toUpperCase());

                //int id, String username, String password, String email, String avatarPath, Gender gender
                Client client = new Client(clientId, email, username, password, avatarPath, gender);
                return client;
            }
        } catch (SQLException e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
        return null;
    }

    /**
     * load list of borrowed book ids for each client.
     * @param clientId id of client
     * @return list of borrowed book id for each client
     */
    private static List<Integer> loadBorrowedBooks(int clientId) {
        List<Integer> borrowedBooks = new ArrayList<>();
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(LOAD_BORROWED_BOOKS_QUERY)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int bookID = rs.getInt("book_id");
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
     * @param clientId client id
     * @param bookId   book id
     */
    public static void borrowBook(int clientId, int bookId) {
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(ADD_BORROWED_BOOK_QUERY)) {

            stmt.setInt(1, clientId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error borrowing book: " + e.getMessage());
        }
    }

    /**
     * remove a borrowed book.
     *
     * @param clientId client id
     * @param bookId   book id
     */
    public static void returnBook(int clientId, int bookId) {
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(REMOVE_BORROWED_BOOK_QUERY)) {

            stmt.setInt(1, clientId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
    }

    /**
     * mark a book as overdue.
     * @param clientId client id
     * @param bookId book id
     */
    public static void markBookAsOverdue(int clientId, int bookId) {
        try (Connection connection = dbService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(MARK_BOOK_AS_OVERDUE_QUERY)) {

            stmt.setInt(1, clientId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error marking book as overdue: " + e.getMessage());
        }
    }

    public static boolean addNewClient(Client client) {
        String insertQuery = "INSERT INTO clients (username, email, password, gender) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbService.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, client.getUsername());
                statement.setString(2, client.getEmail());
                statement.setString(3, client.getPassword());
                statement.setString(4, Gender.takeGender(client.getGender()));

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred.");
        }
        return false;
    }
}