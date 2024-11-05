package edu.lms.controllers;

import edu.lms.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SignInController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePassword;
    @FXML
    private ImageView openEyes;
    @FXML
    private ImageView closedEyes;
    private boolean visibility;

    @FXML
    public void initialize() {
        visiblePassword.textProperty().bindBidirectional(passwordField.textProperty());
    }

    public void switchVisibility() {
        visibility = !visibility;
        passwordField.setVisible(visibility);
        openEyes.setVisible(visibility);

        visiblePassword.setVisible(!visibility);
        closedEyes.setVisible(!visibility);
    }

    private void checkCredentials() {

    }
}
