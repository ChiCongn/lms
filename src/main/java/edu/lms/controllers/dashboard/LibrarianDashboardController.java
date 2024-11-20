package edu.lms.controllers.dashboard;

import edu.lms.models.book.BookManager;

import edu.lms.models.user.Librarian;
import edu.lms.models.user.UserManager;

import edu.lms.services.AlertDialog;
import edu.lms.services.database.BorrowedBookDataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianDashboardController extends DashboardController implements Initializable {
    @FXML
    private Label numberOfBooksLabel;

    @FXML
    private Label numberOfBorrowedBooksLabel;

    @FXML
    private Label numberOfClientsLabel;

    @FXML
    private TextField searchText;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView avatar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(librarian.getUsername());
        avatar = new ImageView(librarian.getAvatarPath());
        numberOfBooksLabel.setText(Integer.toString(BookManager.getNumberOfBooks()));
        numberOfBorrowedBooksLabel.setText(Integer.toString(BorrowedBookDataService.getNumberOfBorrowedBook()));
        numberOfClientsLabel.setText(Integer.toString(UserManager.getNumberOfClients()));
    }

    @FXML
    private void showConfirmationAlert() {
        Alert confirmation = AlertDialog.makeConfirmationAlert("test", "click ok :)");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("ok confirm");
            } else {
                System.out.println("cancel");
            }
        });
    }

}
