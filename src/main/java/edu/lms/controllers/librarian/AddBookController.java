package edu.lms.controllers.librarian;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.review.Review;
import edu.lms.models.review.ReviewCell;
import edu.lms.services.GoogleBooksAPI;
import edu.lms.services.database.BookDao;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class AddBookController implements Initializable {
    private static final int DEFAULT_TOTAL_COPIES = 100;
    @FXML
    private Label authorsLabel;

    @FXML
    private TextArea descriptionText;

    @FXML
    private Label languageLabel;

    @FXML
    private Label pageCountLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField totalCopiesField;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Label ratingLabel;

    @FXML
    private Tooltip ratingTooltip;

    @FXML
    private TextField searchTextField;

    @FXML
    private ListView<Book> searchResult;

    @FXML
    private Label noBookSelectedWarning;

    @FXML
    private Label successfullyAddBook;

    @FXML
    private Label addedBook;

    private Book selectedBook;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureListView();
        ratingTooltip.setStyle("-fx-font-size: 14;");
        ratingTooltip.setShowDelay(Duration.millis(500));
        Tooltip.install(ratingLabel, ratingTooltip);
    }

    @FXML
    public void searchBook(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("search :(");

            String query = searchTextField.getText();
            if (!query.isEmpty()) {
                ObservableList<Book> filteredBooks = GoogleBooksAPI.searchBooks(query);
                searchResult.setItems(filteredBooks);
                searchResult.setVisible(!filteredBooks.isEmpty());
            }
        }
    }

    private void configureListView() {
        System.out.println("set config list view");
        searchResult.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label titleText = new Label();
            private final HBox content = new HBox(imageView, titleText);

            {
                imageView.setFitHeight(60);
                imageView.setFitWidth(40);
                content.setSpacing(5);
            }

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setGraphic(null);
                } else {
                    titleText.setText(book.getTitle());
                    imageView.setImage(new Image(book.getCoverImage(), true));
                    setGraphic(content);
                }
            }
        });

        searchResult.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBook = newSelection;
                showBookDetails();
            }
        });
    }

    private void showBookDetails() {
        titleLabel.setText(selectedBook.getTitle());
        authorsLabel.setText(selectedBook.getAuthors());
        pageCountLabel.setText(Integer.toString(selectedBook.getPageCount()));
        languageLabel.setText(selectedBook.getLanguage());
        descriptionText.setText(selectedBook.getDescription());
        thumbnail.setImage(new Image(selectedBook.getCoverImage()));
        ratingLabel.setText(ReviewCell.loadRating(selectedBook.getRating().intValue()));
        ratingLabel.setTextFill(Color.GOLD);
        ratingTooltip.setText(selectedBook.getRating().toString());
    }

    @FXML
    private void addBookIntoDatabase() {
        if (selectedBook == null) {
            noBookSelectedWarning.setVisible(true);
            return;
        }
        noBookSelectedWarning.setVisible(false);
        int totalCopies = getTotalCopiesOrDefault();
        selectedBook.setTotalCopies(totalCopies);
        selectedBook.setAvailableCopies(totalCopies);
        if (BookDao.addBook(selectedBook)) {
            successfullyAddBook.setVisible(true);
            BookManager.insertBook(selectedBook);
            PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
            hideLabel.setOnFinished(e -> successfullyAddBook.setVisible(false));
            hideLabel.play();
        } else {
            addedBook.setVisible(true);
            PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
            hideLabel.setOnFinished(e -> addedBook.setVisible(false));
            hideLabel.play();
        }
    }

    private int getTotalCopiesOrDefault() {
        try {
            return Integer.parseInt(totalCopiesField.getText());
        } catch (NumberFormatException e) {
            return DEFAULT_TOTAL_COPIES;
        }
    }

}
