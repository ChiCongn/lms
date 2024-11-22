package edu.lms.services.database;

import edu.lms.services.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = Config.DATABASE_ENDPOINT;
    private static final String USER = Config.DATABASE_USERNAME;
    private static final String PASSWORD = Config.DATABASE_PASSWORD;
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    /*public static void main(String[] args) {
        String[] msv = {"23021461", "23021465", "23021469", "23021473", "23021477"};
        String[] name = {"chauanh", "nguyenanh", "maianh", "vietanh", "vanbien"};
        String insertQuery = "INSERT INTO clients (username, email, password, avatar_path, gender) VALUES (?, ?, ?, ?, ?)";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = DatabaseService.getInstance().getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    statement.setString(1, name[i]);
                    statement.setString(2, msv[i] + "@vnu.edu.vn");
                    statement.setString(3, name[i] + "Ca@3");
                    //edu/lms/images/chart.png
                    statement.setString(4, "edu/lms/images/" + name + ".png");
                    statement.setString(5, "other");

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("success");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database error occurred.");
            }
        }

    }*/
}
