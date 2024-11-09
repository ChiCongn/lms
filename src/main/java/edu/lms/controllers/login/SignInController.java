package edu.lms.controllers.login;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.dashboard.AdminDashboardController;
import edu.lms.controllers.dashboard.ClientDashboardController;
import edu.lms.controllers.dashboard.LibrarianDashboardController;
import edu.lms.models.user.Role;
import edu.lms.services.database.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.lms.models.user.Role.ADMIN;
import static edu.lms.models.user.Role.LIBRARIAN;

public class SignInController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField password;
    @FXML
    private TextField visiblePassword;
    @FXML
    private ImageView openEyes;
    @FXML
    private ImageView closedEyes;
    @FXML
    private Label incorrectUsername;
    @FXML
    private Label incorrectPassword;
    private boolean visibility;
    private Role role;
    private final DatabaseService instance = DatabaseService.getInstance();

    @FXML
    public void initialize() {
        visiblePassword.textProperty().bindBidirectional(password.textProperty());
    }

    public void switchVisibility() {
        visibility = !visibility;
        password.setVisible(visibility);
        openEyes.setVisible(visibility);

        visiblePassword.setVisible(!visibility);
        closedEyes.setVisible(!visibility);
    }

    public void switchSignUpController() {
        SignUpController signUpController = SceneManager.switchScene(Constants.SIGNUP_VIEW);
    }

    public void checkCredentials() {
        System.out.println("check credential");
        try {
            if (checkUserRole(usernameField.getText().trim(), password.getText().trim())) {
                System.out.println("sign in :)");
                switchToDashboard();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkUserRole(String username, String password) throws SQLException {
        if (checkCredentialsInTable("admins", username, password)) {
            role = Role.ADMIN;
            System.out.println("is admin");
            return true;
        }
        else if (checkCredentialsInTable("librarians", username, password)) {
            role = Role.LIBRARIAN;
            System.out.println("is librarian");
            return true;
        } else if (checkCredentialsInTable("clients", username, password)) {
            role = Role.CLIENT;
            System.out.println("is client");
            return true;
        }
        System.out.println("unknown error");
        incorrectUsername.setVisible(true);
        incorrectPassword.setVisible(true);
        return false;
    }

    private boolean checkCredentialsInTable(String tableName, String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE username = ? AND password = ?";

        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void switchToDashboard() {
        if (role == null) return;
        if (role == ADMIN) {
            AdminDashboardController adminDashboardController = SceneManager.switchScene(Constants.ADMIN_DASHBOARD_VIEW);
        } else if (role == LIBRARIAN) {
            LibrarianDashboardController librarianDashboardController = SceneManager.switchScene(Constants.LIBRARIAN_DASHBOARD_VIEW);
        } else {
            ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW);
        }
    }
}
