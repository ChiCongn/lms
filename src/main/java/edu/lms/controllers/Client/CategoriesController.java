//package edu.lms.controllers.client;
//
//import edu.lms.models.book.Book;
//import edu.lms.models.book.Card;
//import edu.lms.models.category.CategoriesManager;
//import javafx.collections.ObservableList;
//import javafx.concurrent.Task;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//
//import java.net.URL;
//import java.util.Map;
//import java.util.ResourceBundle;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class CategoriesController extends DashboardController implements Initializable {
//
//    @FXML
//    private Label usernameLabel;
//
//    @FXML
//    private ImageView avatarImage;
//
//    @FXML
//    private ScrollPane scrollPane;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        usernameLabel.setText(client.getUsername());
//        avatarImage.setImage(new Image(client.getAvatarPath()));
//        VBox container = new VBox();
//        //ExecutorService executorService = Executors.newFixedThreadPool(4);
//
//        /*Map<String, ObservableList<Book>> groupedBooks = CategoriesManager.getCategories();
//
//        for (Map.Entry<String, ObservableList<Book>> entry : groupedBooks.entrySet()) {
//            String category = entry.getKey();
//            ObservableList<Book> booksInCategory = entry.getValue();
//
//            Label categoryLabel = new Label(category);
//            categoryLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
//
//            HBox containerHBox = new HBox(5);
//            containerHBox.setPrefHeight(200);
//
//            for (Book book : booksInCategory) {
//                VBox placeholder = createPlaceholderCard();
//                containerHBox.getChildren().add(placeholder);
//
//                Task<Card> loadBookCardTask = new Task<>() {
//                    @Override
//                    protected Card call() {
//                        return new Card(book);
//                    }
//                };
//
//                loadBookCardTask.setOnSucceeded(e -> {
//                    Card bookCard = loadBookCardTask.getValue();
//                    int index = containerHBox.getChildren().indexOf(placeholder);
//                    containerHBox.getChildren().set(index, bookCard);
//                });
//
//                executorService.submit(loadBookCardTask);
//            }
//
//            ScrollPane categoryScrollPane = new ScrollPane(containerHBox);
//            categoryScrollPane.setPrefHeight(200);
//
//            container.getChildren().addAll(categoryLabel, categoryScrollPane);
//        }
//
//        scrollPane.setContent(container); // Corrected method
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            executorService.shutdown();
//            try {
//                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
//                    executorService.shutdownNow();
//                }
//            } catch (InterruptedException e) {
//                executorService.shutdownNow();
//            }
//        }));*/
//    }
//
//
//    private VBox createPlaceholderCard() {
//        VBox placeholder = new VBox();
//        placeholder.setPrefSize(100, 150);
//        placeholder.setStyle("-fx-background-color: lightgray; -fx-border-color: gray;");
//        return placeholder;
//    }
//}
