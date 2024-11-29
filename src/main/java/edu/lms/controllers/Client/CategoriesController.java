package edu.lms.controllers.client;

import edu.lms.models.book.Book;
import edu.lms.models.book.VerticalCard;
import edu.lms.models.book.CategoriesManager;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class CategoriesController extends DashboardController implements Initializable {

    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(client.getUsername());
        avatarImage.setImage(new Image(client.getAvatarPath()));

        Map<String, ObservableList<Book>> groupedBooks = CategoriesManager.getCategories();

        for (Map.Entry<String, ObservableList<Book>> entry : groupedBooks.entrySet()) {
            String category = entry.getKey();
            ObservableList<Book> booksInCategory = entry.getValue();

            Label categoryLabel = new Label(category);
            categoryLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            HBox containerHBox = new HBox(5);
            containerHBox.setPrefHeight(200);

            int booksToShow = Math.min(10, booksInCategory.size());
            /*for (int i = 0; i < booksToShow; i++) {
                Book book = booksInCategory.get(i);

                VBox placeholder = createPlaceholderCard();
                containerHBox.getChildren().add(placeholder);

                Task<VerticalCard> loadBookCardTask = new Task<>() {
                    @Override
                    protected VerticalCard call() {
                        return new VerticalCard(book);
                    }
                };

                loadBookCardTask.setOnSucceeded(e -> {
                    VerticalCard bookCard = loadBookCardTask.getValue();
                    int index = containerHBox.getChildren().indexOf(placeholder);
                    containerHBox.getChildren().set(index, bookCard);
                });

                loadBookCardTask.setOnFailed(event -> {
                    Throwable exception = loadBookCardTask.getException();
                    System.err.println("Initialization failed: " + exception.getMessage());
                });
                new Thread(loadBookCardTask).start();

            }*/
            Task<Void> loadBooksTask = new Task<>() {
                @Override
                protected Void call() {
                    for (int i = 0; i < booksToShow; i++) {
                        Book book = booksInCategory.get(i);

                        // Create a placeholder card
                        VBox placeholder = createPlaceholderCard();

                        // Use Platform.runLater to update the UI from the background thread
                        Platform.runLater(() -> containerHBox.getChildren().add(placeholder));

                        // Create and load the actual book card
                        VerticalCard bookCard = new VerticalCard(book);
                        bookCard.setOnMouseClicked(mouseEvent -> {
                            if (mouseEvent.getClickCount() == 2) {
                                switchToClientBookDetail(book, "categories");
                            }
                        });

                        // Update the UI once the card is created
                        Platform.runLater(() -> {
                            int index = containerHBox.getChildren().indexOf(placeholder);
                            if (index >= 0) {
                                containerHBox.getChildren().set(index, bookCard);
                            }
                        });
                    }
                    return null;
                }
            };

            loadBooksTask.setOnSucceeded(e -> System.out.println("Books loaded successfully!"));
            loadBooksTask.setOnFailed(e -> System.err.println("Error loading books: " + loadBooksTask.getException().getMessage()));

            new Thread(loadBooksTask).start();


            ScrollPane categoryScrollPane = new ScrollPane(containerHBox);
            categoryScrollPane.setPrefHeight(220);
            categoryScrollPane.setPrefWidth(1000);
            container.getChildren().addAll(categoryLabel, categoryScrollPane);
        }
        container.setPrefHeight(groupedBooks.size() * 250);
    }


    private VBox createPlaceholderCard() {
        VBox placeholder = new VBox();
        placeholder.setPrefSize(150, 200);
        placeholder.setStyle("-fx-background-color: lightgray; -fx-border-color: gray;");
        return placeholder;
    }
}
