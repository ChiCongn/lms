package edu.lms.controllers.login;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.common.SoundManager;
import edu.lms.services.EmailService;
import edu.lms.services.Validator;
import edu.lms.services.VerificationCode;
import edu.lms.services.database.DatabaseConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ForgotPasswordController implements Initializable {
    @FXML
    private TextField codeText;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField newPassword;

    @FXML
    private TextField usernameText;

    @FXML
    private TextField visibleConfirmPasswordTextField;

    @FXML
    private TextField visibleNewPasswordTextField;

    @FXML
    private Label verificationCodeLabel;

    @FXML
    private ImageView openEyesNewPassword;

    @FXML
    private ImageView closedEyesNewPassword;

    @FXML
    private ImageView openEyesConfirmPassword;

    @FXML
    private ImageView closedEyesConfirmPassword;

    @FXML
    private Label passwordMatchLabel;

    @FXML
    private Label passwordStrengthLabel;

    @FXML
    private Label emailOrPasswordWrong;

    @FXML
    public Label invalidEmail;

    private boolean visibleNewPassword;

    private boolean visibleConfirmPassword;

    private int currentVerificationCode;

    private boolean isStrongPassword;
    private boolean matchedPassword;
    private boolean isVerification;
    private boolean isSent;
    private boolean isValidEmail;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visibleNewPasswordTextField.textProperty().bindBidirectional(newPassword.textProperty());
        visibleConfirmPasswordTextField.textProperty().bindBidirectional(confirmPassword.textProperty());
        emailTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldEmail, String newEmail) {
                if (!newEmail.isEmpty()) {
                    if (!Validator.checkValidEmail(newEmail)) {
                        invalidEmail.setText("Invalid email! Pls choose other email!");
                        invalidEmail.setStyle("-fx-text-fill: red;");
                        isValidEmail = false;
                    } else {
                        invalidEmail.setText("Valid email");
                        invalidEmail.setStyle("-fx-text-fill: green;");
                        isValidEmail = true;
                    }

                }
            }
        });
        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPasswordMatch();
            checkPasswordStrength(newValue);
        });
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> checkPasswordMatch());
    }

    public void backToSignInController() {
        SignInController signInController = SceneManager.switchScene(Constants.SIGN_IN_VIEW, false);
        System.out.println("back to sign");
    }

    @FXML
    private void changePassword() {
        if (!isStrongPassword || !matchedPassword || isVerification) {
            return;
        }
        String username = usernameText.getText();
        String newPassword = visibleNewPasswordTextField.getText();
        if (checkCredentials(username, emailTextField.getText())) {
            emailOrPasswordWrong.setVisible(true);
            return;
        }

        String changePasswordQuery = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(changePasswordQuery)) {

            statement.setString(1, newPassword);
            statement.setString(2, username);
            System.out.println("change password");
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error changing password");
        }
    }
    @FXML
    private void switchVisibleNewPassword() {
        visibleNewPassword = !visibleNewPassword;
        visibleNewPasswordTextField.setVisible(visibleNewPassword);
        closedEyesNewPassword.setVisible(visibleNewPassword);

        newPassword.setVisible(!visibleNewPassword);
        openEyesNewPassword.setVisible(!visibleNewPassword);
    }

    @FXML
    private void switchVisibleConfirmPassword() {
        visibleConfirmPassword = !visibleConfirmPassword;
        visibleConfirmPasswordTextField.setVisible(visibleConfirmPassword);
        closedEyesConfirmPassword.setVisible(visibleConfirmPassword);

        confirmPassword.setVisible(!visibleConfirmPassword);
        openEyesConfirmPassword.setVisible(!visibleConfirmPassword);
    }

    @FXML
    private void sendVerificationCode() {
        currentVerificationCode = VerificationCode.generateVerificationCode();
        String toEmail = emailTextField.getText();
        System.out.println("send code now");
        if (!isValidEmail) {
            return;
        }
        new Thread(() -> {
            EmailService.sendVerificationCode(toEmail, currentVerificationCode);
        }).start();
        if (!isSent) {
            isSent = true;
            verificationCodeLabel.setText("Check your email!");
            verificationCodeLabel.setStyle("-fx-text-fill: green;");
        }
    }

    @FXML
    private void checkVerificationCode() {
        String verificationCode = codeText.getText();
        if (verificationCode == null || verificationCode.isEmpty()) return;
        int currentCode = Integer.parseInt(codeText.getText());
        if (currentCode != currentVerificationCode) {
            isVerification = false;
            verificationCodeLabel.setText("Incorrect code. Please try again.");
            verificationCodeLabel.setStyle("-fx-text-fill: red;");
        } else {
            verificationCodeLabel.setText("Correct! Change password now");
            verificationCodeLabel.setStyle("-fx-text-fill: green;");
        }
    }

    private void checkPasswordMatch() {
        if (newPassword.getText().equals(confirmPassword.getText())) {
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

    private boolean checkCredentials(String username, String email) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, email);

            ResultSet resultSet = statement.executeQuery();
            System.out.println("check email and username is existed in database");
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
