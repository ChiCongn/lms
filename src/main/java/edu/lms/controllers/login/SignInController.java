package edu.lms.controllers.login;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.dashboard.AdminDashboardController;
import edu.lms.controllers.dashboard.DashboardController;
import edu.lms.controllers.dashboard.LibrarianDashboardController;
import edu.lms.models.user.Librarian;
import edu.lms.services.database.DatabaseConnection;
import edu.lms.services.database.UsersDataService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
    @FXML
    private ProgressBar loadingBar;
    private boolean visibility;
    private String role;
    private final DatabaseConnection instance = DatabaseConnection.getInstance();
    private int userId;


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
        SignUpController signUpController = SceneManager.switchScene(Constants.SIGNUP_VIEW, false);
    }

    public void signIn() {
        System.out.println("check credential");
        try {
            if (checkCredentials(usernameField.getText().trim(), password.getText().trim())) {
                System.out.println("sign in :)");
                switchToDashboard();
            } else {
                System.out.println("unknown");
                incorrectUsername.setVisible(true);
                incorrectPassword.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private boolean checkCredentials(String username, String password) throws SQLException {
        String query = "SELECT user_id, role FROM users WHERE username = ? AND password = ?";

        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString("role");
                userId = resultSet.getInt("user_id");
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void switchToDashboard() {
        if (role == null) return;
        if (role.equals("admin")) {
            AdminDashboardController adminDashboardController = SceneManager.switchScene(Constants.ADMIN_DASHBOARD_VIEW, true);
        } else if (role.equals("librarian")) {
            System.out.println("sign in with librarian role");
            Librarian librarian = (Librarian) UsersDataService.loadUserData(userId);
            DashboardController.setLibrarian(librarian);
            LibrarianDashboardController librarianDashboardController = SceneManager.switchScene(Constants.LIBRARIAN_DASHBOARD_VIEW, true);
        } else {
            //ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW);
            System.out.println("nothing");
        }
    }

}
