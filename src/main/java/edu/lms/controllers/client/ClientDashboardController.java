package edu.lms.controllers.client;

import edu.lms.Constants;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.controllers.book.HorizontalCard;
import edu.lms.models.book.VerticalCard;
import edu.lms.models.user.ClientDataManager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientDashboardController extends DashboardController implements Initializable {

    @FXML
    private HBox topChoiceBooksContainer;

    @FXML
    private HBox recentBooksContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(client.getUsername());
        avatarImage.setImage(new Image(client.getAvatarPath()));
        initializeRecentBooks();
        initializeTopChoiceBooks();
    }

    private void initializeRecentBooks() {
        List<Book> recentBooks = ClientDataManager.getRecentBooks();
        if (recentBooks == null || recentBooks.isEmpty()) {
            return;
        }

        Platform.runLater(() -> {
            for (int i = recentBooks.size() - 1; i >= 0; i--) {
                Book book = recentBooks.get(i);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource(Constants.HORIZONTAL_CARD_VIEW));
                    HBox cardBox = fxmlLoader.load();
                    HorizontalCard cardController = fxmlLoader.getController();
                    cardController.setData(book);
                    recentBooksContainer.getChildren().add(cardBox);
                    cardBox.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            switchToClientBookDetail(book, "dashboard");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeTopChoiceBooks() {
        List<Book> topChoiceBooks = BookManager.getTopChoiceBooks();
        System.out.println("set up top choice books");
        setUpTopChoiceBooksData(topChoiceBooks);

        topChoiceBooksContainer.setPrefWidth(2000);
        /*for (Book book : topChoiceBooks) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(Constants.VERTICAL_CARD_VIEW));
                VBox cardBox = fxmlLoader.load();
                VerticalCard cardController = fxmlLoader.getController();
                cardController.setData(book);
                cardBox.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) {
                        switchToClientBookDetail(book);
                    }
                });
                topChoiceBooksContainer.getChildren().add(cardBox);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
    }

    private void setUpTopChoiceBooksData(List<Book> topChoiceBooks) {
        Task<Void> loadTopChoiceBooksTask = new Task<>() {
            @Override
            protected Void call() {
                for (Book book : topChoiceBooks) {

                    VBox bookCard = new VerticalCard(book);
                    bookCard.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            switchToClientBookDetail(book, "dashboard");
                        }
                    });

                    // Updating the UI safely on the JavaFX Application Thread
                    Platform.runLater(() -> topChoiceBooksContainer.getChildren().add(bookCard));
                }
                return null;
            }
        };

        loadTopChoiceBooksTask.setOnSucceeded(e -> System.out.println("Books loaded successfully!"));
        loadTopChoiceBooksTask.setOnFailed(e -> System.err.println("Error loading books: " + loadTopChoiceBooksTask.getException().getMessage()));

        new Thread(loadTopChoiceBooksTask).start();
    }

}
