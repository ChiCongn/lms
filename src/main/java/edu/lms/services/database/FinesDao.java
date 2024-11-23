package edu.lms.services.database;

import edu.lms.models.book.BorrowedBook;
import javafx.scene.chart.PieChart;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinesDao {
    private static final String CALCULATE_TOTAL_FINES_QUERY = "SELECT SUM(fine_amount) FROM fines WHERE paid = false";
    private static final String ADD_FINES_QUERY = "INSERT INTO fines (user_id, borrow_id, fine_amount) VALUE (?, ?, ?)";
    private static final String PAY_FINES_QUERY = "UPDATE fines SET paid = true WHERE borrow_id = ?";


    public static BigDecimal calculateTotalFines() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CALCULATE_TOTAL_FINES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getBigDecimal(1);
            }
        } catch (SQLException e) {
            System.err.println("Error calculating total fines: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    public static boolean addFines(BorrowedBook overdueBook) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_FINES_QUERY)) {

            statement.setInt(1, overdueBook.getClientId());
            statement.setInt(2, overdueBook.getBorrowId());
            statement.setBigDecimal(3, overdueBook.getFines());
            System.out.println("add new fines");
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding new fines: " + e.getMessage());
        }
        return false;
    }

    public static boolean payFines(int borrowId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(PAY_FINES_QUERY)) {

            statement.setInt(1, borrowId);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("paying fines has borrow id: " + borrowId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error paying fines");
        }
        return false;
    }
}

