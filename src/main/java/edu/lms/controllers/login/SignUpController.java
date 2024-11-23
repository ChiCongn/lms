package edu.lms.controllers.login;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.Gender;
import edu.lms.services.database.UsersDao;
import edu.lms.services.database.DatabaseConnection;
import edu.lms.services.EmailService;
import edu.lms.services.Validator;
import edu.lms.services.VerificationCode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
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
    public Label isUsernameTaken;
    @FXML
    public Label invalidEmail;
    @FXML
    public ImageView successfulRegistration;
    @FXML
    public Button continuee;
    @FXML
    private ChoiceBox<Gender> genderChoiceBox;
    private boolean visibility;
    private boolean confirmVisibility;
    private boolean isStrongPassword;
    private boolean matchedPassword;
    private boolean isVerification;
    private boolean isSent;
    private boolean isValidUsername;
    private boolean isValidEmail;
    private int currentVerificationCode;
    private final DatabaseConnection instance = DatabaseConnection.getInstance();

    public void backToSignInController() {
        SignInController signInController = SceneManager.switchScene(Constants.SIGN_IN_VIEW, false);
        System.out.println("back to sign");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visiblePassword.textProperty().bindBidirectional(password.textProperty());
        visibleConfirmPassword.textProperty().bindBidirectional(confirmPassword.textProperty());

        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldUsername, String newUsername) {
                if (!newUsername.isEmpty()) {
                    //System.out.println("check is taken user name");
                    if (checkUsernameTaken(newUsername)) {
                        isUsernameTaken.setText("username has been taken! Pls try other username!");
                        isUsernameTaken.setStyle("-fx-text-fill: red;");
                    } else {
                        isUsernameTaken.setText("username is available!");
                        isUsernameTaken.setStyle("-fx-text-fill: green;");
                    }
                }
            }
        });

        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldEmail, String newEmail) {
                if (!newEmail.isEmpty()) {
                    if (!Validator.checkValidEmail(newEmail)) {
                        invalidEmail.setText("Invalid email! Pls choose other email!");
                        invalidEmail.setStyle("-fx-text-fill: red;");
                        return;
                    }
                    if (checkUniqueEmail(newEmail)) {
                        invalidEmail.setText("Email is available!");
                        invalidEmail.setStyle("-fx-text-fill: green;");
                    } else {
                        invalidEmail.setText("this email is taken! Pls choose other email!");
                        invalidEmail.setStyle("-fx-text-fill: red;");
                    }
                }
            }
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPasswordMatch();
            checkPasswordStrength(newValue);
        });
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> checkPasswordMatch());
        //genderChoiceBox.getItems().addAll("Male", "Female", "Other");
        genderChoiceBox.getItems().addAll(Gender.MALE, Gender.FEMALE, Gender.OTHER);
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
        if (!isValidEmail) {
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
            isVerification = false;
            verificationCodeLabel.setText("Incorrect code. Please try again.");
            verificationCodeLabel.setStyle("-fx-text-fill: red;");
        } else {
            verificationCodeLabel.setText("Correct! Sign up now!");
            verificationCodeLabel.setStyle("-fx-text-fill: green;");
        }
    }

    // Have not been tested!
    public void registerAccount() {
        if (!isStrongPassword || !matchedPassword || isVerification) {
            return;
        }

        System.out.println("register");
        Client client = new Client(username.getText(), password.getText(), email.getText(), genderChoiceBox.getValue());
        if (UsersDao.addNewClient(client)) {
            successfulRegistration.setVisible(true);
            continuee.setVisible(true);
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

    private boolean checkUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //System.out.println("username is available!");
                isValidUsername = resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValidUsername;
    }

    private boolean checkUniqueEmail(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection connection = instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValidEmail = resultSet.getInt(1) < 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValidEmail;
    }

}
