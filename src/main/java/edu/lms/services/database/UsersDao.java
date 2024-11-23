package edu.lms.services.database;

import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;

public class UsersDao {
    private static final String LOAD_CLIENTS_QUERY = "SELECT * FROM users WHERE role = 'client'";
    private static final String LOAD_USER_QUERY = "SELECT * FROM users WHERE user_id = ?";
    private static final String LOAD_FINES_OF_THIS_CLIENT = "SELECT SUM(fines.fine_amount) AS outstanding_fines FROM fines WHERE user_id = ? AND paid = false";
    private static final String ADD_NEW_CLIENT_QUERY = "INSERT INTO users (username, email, password, gender) VALUES (?, ?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE user_id = ?";
    private static final String SUSPEND_USER_QUERY = "UPDATE users SET status = 'suspended' WHERE user_id = ?";
    private static final String REACTIVATE_USER_QUERY = "UPDATE users SET status = 'active' WHERE user_id = ?";

    /**
     * load all list of clients form database.
     * @return list of clients
     */
    public static ObservableList<Client> loadClientsData() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(LOAD_CLIENTS_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int clientId = rs.getInt("user_id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String avatarPath = rs.getString("avatar_path");
                String status = rs.getString("status");
                String genderString = rs.getString("gender");
                Gender gender = Gender.valueOf(genderString.toUpperCase());
                ObservableList<BorrowedBook> borrowedBooks = BorrowedBookDao.loadBorrowedBooks(clientId);

                BigDecimal outstandingFines = loadOutstandingFinesOfThisClient(clientId);
                //int id, String username, String password, String email, String avatarPath, Gender gender
                Client client = new Client(clientId, username, password, email, avatarPath, status, gender, borrowedBooks, outstandingFines);
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
        System.out.println("load all client from database");
        return clients;
    }

    public static User loadUserData(int userId) {
        System.out.println("load user data");
        User user = null;
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_USER_QUERY)) {

            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String avatarPath = rs.getString("avatar_path");
                String status = rs.getString("status");
                String role = rs.getString("role");
                String genderString = rs.getString("gender");
                Gender gender = Gender.valueOf(genderString.toUpperCase());

                //int id, String username, String password, String email, String avatarPath, Gender gender
                if (role.equals("admin")) {
                    System.out.println("you are admin");
                    user = new Admin(userId, email, username, password, avatarPath, status, gender);
                } else if (role.equals("librarian")) {
                    System.out.println("you are librarian");
                    user = new Librarian(userId, email, username, password, avatarPath, status, gender);
                } else {
                    System.out.println("you are client");
                    user = new Client(userId, email, username, password, avatarPath, status, gender);
                }

                System.out.println("load data of specific user");
            }
        } catch (SQLException e) {
            System.err.println("Error loading user: " + e.getMessage());
        }
        return user;
    }

    public static boolean addNewClient(Client client) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(ADD_NEW_CLIENT_QUERY)) {
                statement.setString(1, client.getUsername());
                statement.setString(2, client.getEmail());
                statement.setString(3, client.getPassword());
                statement.setString(4, Gender.takeGender(client.getGender()));

                System.out.println("register client then insert into database");
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding new client: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteUser(int userId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {

            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Delete user successfully.");
                return true;
            } else {
                System.out.println("User not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    public static boolean suspendUser(int userId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SUSPEND_USER_QUERY)) {

            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Suspend user successfully.");
                return true;
            } else {
                System.out.println("User not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error suspending user: " + e.getMessage());
        }
        return false;
    }

    public static boolean reactivateUser(int userId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(REACTIVATE_USER_QUERY)) {

            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reactivate user successfully.");
                return true;
            } else {
                System.out.println("User not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error reactivating user: " + e.getMessage());
        }
        return false;
    }

    private static BigDecimal loadOutstandingFinesOfThisClient(int clientId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_FINES_OF_THIS_CLIENT)) {

            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("load outstanding fines of specific client");
                return resultSet.getBigDecimal("outstanding_fines");
            }
        } catch (SQLException e) {
            System.err.println("Error loading total fines of this client: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}