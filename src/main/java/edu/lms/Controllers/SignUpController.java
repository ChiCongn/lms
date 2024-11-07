package edu.lms.controllers;

import edu.lms.Constants;
import edu.lms.Controllers.SceneManager;
import edu.lms.services.database.DatabaseService;
import edu.lms.services.EmailService;
import edu.lms.services.Validator;
import edu.lms.services.VerificationCode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private TextField visiblePassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField visibleConfirmPassword;
    @FXML
    private ImageView openEyes;
    @FXML
    private ImageView closedEyes;
    @FXML
    private ImageView openEyes2;
    @FXML
    private ImageView closedEyes2;
    @FXML
    private Label passwordMatchLabel;
    @FXML
    private Label passwordStrengthLabel;
    @FXML
    private TextField verificationCode;
    @FXML
    private Button sendCode;
    @FXML
    public Label verificationCodeLabel;
    @FXML
    public Label invalidUsername;
    @FXML
    public Label invalidEmail;
    @FXML
    public ImageView successfulRegistration;
    @FXML
    public Button continuee;
    private boolean visibility;
    private boolean confirmVisibility;
    private boolean isStrongPassword;
    private boolean matchedPassword;
    private boolean isVerification;
    private boolean isSent;
    private int currentVerificationCode;
    private DatabaseService instance = DatabaseService.getInstance();

    public void backToSignInController() {
        edu.lms.controllers.SignInController signInController = SceneManager.switchScene(Constants.SIGN_IN_VIEW);
        System.out.println("back to sign");
    }

    @FXML
    public void initialize() {
        visiblePassword.textProperty().bindBidirectional(password.textProperty());
        visibleConfirmPassword.textProperty().bindBidirectional(confirmPassword.textProperty());

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPasswordMatch();
            checkPasswordStrength(newValue);
        });
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> checkPasswordMatch());
    }

    public void switchVisibility() {
        visibility = !visibility;
        password.setVisible(visibility);
        openEyes.setVisible(visibility);

        System.out.println("set visibility");
        visiblePassword.setVisible(!visibility);
        closedEyes.setVisible(!visibility);
    }

    public void switchConfirmVisibility() {
        confirmVisibility = !confirmVisibility;
        confirmPassword.setVisible(confirmVisibility);
        openEyes2.setVisible(confirmVisibility);

        System.out.println("set confirm visibility");
        visibleConfirmPassword.setVisible(!confirmVisibility);
        closedEyes2.setVisible(!confirmVisibility);
    }

    public void sendVerificationCode() {
        currentVerificationCode = VerificationCode.generateVerificationCode();
        String toEmail = email.getText();
        if (!Validator.checkValidEmail(toEmail)) {
            invalidEmail.setVisible(true);
            return;
        }
        EmailService.sendVerificationCode(toEmail, currentVerificationCode);
        if (!isSent) {
            isSent = true;
            sendCode.setText("Resend");
            verificationCodeLabel.setText("Check your email!");
            verificationCodeLabel.setStyle("-fx-text-fill: green;");
        }
    }

    public void checkVerificationCode() {
        String code = verificationCode.getText();
        if (code == null || code.isEmpty()) return;
        int currentCode = Integer.parseInt(verificationCode.getText());
        if (currentCode != currentVerificationCode) {
            verificationCodeLabel.setText("Incorrect code. Please try again.");
            verificationCodeLabel.setStyle("-fx-text-fill: red;");
        } else {
            verificationCodeLabel.setText("Correct! Sign up now!");
            verificationCodeLabel.setStyle("-fx-text-fill: green;");
        }
    }

    private void checkPasswordMatch() {
        if (password.getText().equals(confirmPassword.getText())) {
            passwordMatchLabel.setText("Passwords match");
            passwordMatchLabel.setStyle("-fx-text-fill: green;");
            matchedPassword = true;
        } else {
            matchedPassword = false;
            passwordMatchLabel.setText("Passwords do not match");
            passwordMatchLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void checkPasswordStrength(String passwordText) {
        if (passwordText.length() >= 8 && passwordText.matches(".*[A-Z].*") &&
                passwordText.matches(".*[a-z].*") && passwordText.matches(".*\\d.*") &&
                passwordText.matches(".*[!@#$%^&*()].*")) {
            isStrongPassword = true;
            passwordStrengthLabel.setText("Strong password");
            passwordStrengthLabel.setStyle("-fx-text-fill: green;");
        } else {
            isStrongPassword = false;
            passwordStrengthLabel.setText("Weak password: Use at least 8 characters with upper/lower case, " +
                    "digit, and special character.");
            passwordStrengthLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Have not been tested!
    /*@FXML
    private void registerAccount() {
        String user = username.getText().trim();
        String emailInput = email.getText().trim();
        String pass = password.getText();

        if (!isStrongPassword || !matchedPassword || !isVerification) {
            return;
        }

        try (Connection connection = instance.getConnection()) {
            String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, user);
                statement.setString(2, emailInput);
                statement.setString(3, pass);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    successfulRegistration.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred.");
        }
    }*/

}
