package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.book.VerticalCard;
import edu.lms.models.user.ClientDataManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BorrowedBooksController extends DashboardController implements Initializable {
    @FXML
    private FlowPane flowPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(client.getUsername());
        avatarImage.setImage(new Image(client.getAvatarPath()));
        ObservableList<BorrowedBook> borrowedBooks = ClientDataManager.getBorrowedBooks();
        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() {
                for (BorrowedBook borrowedBook : borrowedBooks) {

                    VBox bookCard = new VerticalCard(borrowedBook.getBook());
                    bookCard.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            switchToClientBookDetail(borrowedBook.getBook());
                        }
                    });

                    // Updating the UI safely on the JavaFX Application Thread
                    Platform.runLater(() -> flowPane.getChildren().add(bookCard));
                }
                return null;
            }
        };

        loadBooksTask.setOnSucceeded(e -> System.out.println("Books loaded successfully!"));
        loadBooksTask.setOnFailed(e -> System.err.println("Error loading books: " + loadBooksTask.getException().getMessage()));

        new Thread(loadBooksTask).start();
    }
}
