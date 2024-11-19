package edu.lms.services;

import javafx.scene.control.Alert;

public class AlertDialog {
    public static Alert makeConfirmationAlert(String title, String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);
        confirmationAlert.setHeaderText("Are you sure ???");
        confirmationAlert.setContentText(message);
        return confirmationAlert;
    }

    public static Alert makeErrorAlert(String title, String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setContentText(error);
        return errorAlert;
    }
}
