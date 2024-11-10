package edu.lms.services.database;

import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.Client;
import edu.lms.models.user.Gender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDataService {
    private static final String LOAD_CLIENTS_QUERY = "SELECT * FROM clients";
    private static final String LOAD_SINGLE_CLIENT_QUERY = "SELECT * FROM clients WHERE client_id = ?";
    private static final String LOAD_FINES_OF_THIS_CLIENT = "SELECT SUM(fines.fine_amount) AS outstanding_fines FROM fines WHERE client_id = ? AND paid = false";


    /**
     * load all list of clients form database.
     * @return list of clients
     */
    public static ObservableList<Client> loadClientsData() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try (Connection connection = DatabaseService.getInstance().getConnection();
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
                ObservableList<BorrowedBook> borrowedBooks = BorrowedBookDataService.loadBorrowedBooks(clientId);

                BigDecimal outstandingFines = loadOutstandingFinesOfThisClient(clientId);
                //int id, String username, String password, String email, String avatarPath, Gender gender
                Client client = new Client(clientId, username, password, email, avatarPath, gender, borrowedBooks, outstandingFines);
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
        return clients;
    }

    public static Client loadClientsData(int clientId) {
        try (Connection connection = DatabaseService.getInstance().getConnection();
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

    public static boolean addNewClient(Client client) {
        String insertQuery = "INSERT INTO clients (username, email, password, gender) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseService.getInstance().getConnection()) {
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

    private static BigDecimal loadOutstandingFinesOfThisClient(int clientId) {
        try (Connection connection = DatabaseService.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_FINES_OF_THIS_CLIENT)) {

            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("get outstanding fines");
                return resultSet.getBigDecimal("outstanding_fines");
            }
        } catch (SQLException e) {
            System.err.println("Error loading total fines of this client: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}