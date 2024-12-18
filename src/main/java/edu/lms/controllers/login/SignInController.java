package edu.lms.controllers.login;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.client.ClientDashboardController;
import edu.lms.controllers.common.SoundManager;
import edu.lms.controllers.librarian.AdminDashboardController;
import edu.lms.controllers.librarian.DashboardController;
import edu.lms.controllers.librarian.LibrarianDashboardController;
import edu.lms.models.issue.IssuesManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.ClientDataManager;
import edu.lms.models.user.Librarian;
import edu.lms.models.user.UserManager;
import edu.lms.services.database.DatabaseConnection;
import edu.lms.services.database.UsersDao;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
    private Label suspendedAcc;

    private boolean isSuspended;
    private boolean visibility;
    private String role;
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

    @FXML
    private void switchToForgotPasswordView() {
        ForgotPasswordController forgotPasswordController = SceneManager.switchScene(Constants.FORGOT_PASSWORD_VIEW, false);
    }

    private boolean checkCredentials(String username, String password) throws SQLException {
        String query = "SELECT user_id, role, status FROM users WHERE username = ? AND password = ?";
        SoundManager.playSound("mouse-click.wav");
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString("role");
                userId = resultSet.getInt("user_id");
                String status = resultSet.getString("status");
                isSuspended = status.equals("suspended");
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void switchToDashboard() {
        if (isSuspended) {
            suspendedAcc.setVisible(true);
            PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
            hideLabel.setOnFinished(e -> suspendedAcc.setVisible(false));
            hideLabel.play();
            return;
        }
        if (role == null) return;
        if (role.equals("admin")) {
            AdminDashboardController adminDashboardController = SceneManager.switchScene(Constants.ADMIN_DASHBOARD_VIEW, true);
        } else if (role.equals("librarian")) {
            System.out.println("sign in with librarian role");
            loadDataForLibrarian();
            Librarian librarian = (Librarian) UsersDao.loadUserData(userId);
            DashboardController.setData(librarian);
            LibrarianDashboardController librarianDashboardController = SceneManager.switchScene(Constants.LIBRARIAN_DASHBOARD_VIEW, true);
        } else {
            System.out.println("sign in with client role");
            Client client = (Client) UsersDao.loadUserData(userId);
            loadDataForClient();
            edu.lms.controllers.client.DashboardController.setClientData(client);
            ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW, true);
        }
    }

    private void loadDataForLibrarian() {
        Task<Void> loadManagementData = new Task<>() {
            @Override
            protected Void call() {
                UserManager.initialize();
                IssuesManager.initialize();
                return null;
            }
        };

        loadManagementData.setOnSucceeded(event -> {
            System.out.println("Load management data completed successfully!");
        });

        loadManagementData.setOnFailed(event -> {
            Throwable exception = loadManagementData.getException();
            System.err.println("Initialization failed: " + exception.getMessage());
        });

        new Thread(loadManagementData).start();
    }

    private void loadDataForClient() {
        Task<Void> loadClientData = new Task<>() {
            @Override
            protected Void call() {
                ClientDataManager.initialize(userId);
                return null;
            }
        };

        loadClientData.setOnSucceeded(event -> {
            System.out.println("Load management data completed successfully!");
        });

        loadClientData.setOnFailed(event -> {
            Throwable exception = loadClientData.getException();
            System.err.println("Initialization failed: " + exception.getMessage());
        });

        new Thread(loadClientData).start();
    }

}
