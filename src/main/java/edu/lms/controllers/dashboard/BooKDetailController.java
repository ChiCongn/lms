package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.book.Review;
import edu.lms.services.AlertDialog;
import edu.lms.services.database.BookDataService;
import edu.lms.services.database.ReviewDataService;
import edu.lms.services.database.UsersDataService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.math.BigDecimal;


public class BooKDetailController {
    @FXML
    private Label authorsLabel;

    @FXML
    private TextField availableCopiesField;

    @FXML
    private Label availableCopiesLabel;

    @FXML
    private TextArea commentArea;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label invalidTotalCopiesLabel;

    @FXML
    private Label invalidAvailableCopiesLabel;

    @FXML
    private Label invalidPriceLabel;

    @FXML
    private Label pageCountLabel;

    @FXML
    private TextField priceField;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView ratingImage;

    @FXML
    private ListView<Review> reviewsList;

    @FXML
    private ImageView thumbnail;

    @FXML
    private TextField totalCopiesField;

    @FXML
    private Label totalCopiesLabel;

    private Book book;


    public void initialize(Book book) {
        System.out.println("initialize book detail.");
        configureListReviews();
        this.book = book;
        authorsLabel.setText(book.getAuthors());
        pageCountLabel.setText(Integer.toString(book.getPageCount()));
        totalCopiesLabel.setText(Integer.toString(book.getTotalCopies()));
        availableCopiesLabel.setText(Integer.toString(book.getAvailableCopies()));
        priceLabel.setText(book.getPrice().toString());
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setText(book.getDescription());
        descriptionTextArea.setEditable(false);
        reviewsList.setItems(ReviewDataService.loadReviewsOfSpecificBook(book.getBookId()));
        thumbnail.setImage(new Image(book.getCoverImage()));

        totalCopiesField.setOnKeyReleased(keyEvent -> {
            isValidTotalCopies();
        });
    }

    @FXML
    private boolean isValidTotalCopies() {
        String newTotalCopies = totalCopiesField.getText();
        int value = isInteger(newTotalCopies);
        if (value == -1 || value < book.getTotalCopies() - book.getAvailableCopies()) {
            invalidTotalCopiesLabel.setVisible(true);
            return false;
        }
        invalidTotalCopiesLabel.setVisible(false);
        availableCopiesField.setText(Integer.toString(book.getAvailableCopies() + value - book.getTotalCopies()));
        return true;
    }

    @FXML
    private void setTotalCopies() {
        if (!isValidTotalCopies()) return;
        int value = isInteger(totalCopiesField.getText());
        if (!BookDataService.setTotalCopiesOfSpecificBook(book.getBookId(), value)) {
            System.err.println("Error can not set total copies.");
            return;
        }
        int adjustmentAvailableCopies = value - book.getTotalCopies();
        BookDataService.updateAvailableCopiesOfThisBook(book.getBookId(), adjustmentAvailableCopies);
        book.setTotalCopies(value);
        book.setAvailableCopies(book.getAvailableCopies() + adjustmentAvailableCopies);
    }

    @FXML
    private void setAvailableCopies() {
        String newAvailableCopies = availableCopiesField.getText();
        int value = isInteger(newAvailableCopies);
        if (value == -1 || value > book.getTotalCopies()) {
            invalidAvailableCopiesLabel.setVisible(true);
            return;
        }
        invalidAvailableCopiesLabel.setVisible(false);
        if (!BookDataService.setAvailableCopiesOfSpecificBook(book.getBookId(), value)) {
            System.err.println("Error can not set available copies.");
            return;
        }
        book.setAvailableCopies(value);
    }

    @FXML
    private void setPriceBook() {
        String newPrice = priceField.getText();
        BigDecimal value = isDecimal(newPrice);
        if (value == null) {
            invalidPriceLabel.setVisible(false);
            return;
        }
        if (!BookDataService.setPriceOfSpecificBook(book.getBookId(), value)) {
            System.err.println("Error updating book's price.");
        }
        book.setPrice(value);
    }

    @FXML
    private void deleteBook() {
        Alert confirmation = AlertDialog.makeConfirmationAlert("Delete book",
                "Are you sure you want to delete this book?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (!BookDataService.removeBook(book.getBookId())) {
                    System.err.println("something is wrong. Can not delete book!");
                }
                BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW);
                assert booksManagementController != null;
                booksManagementController.deleteBook(book);
                BookManager.deleteBook(book);
                System.out.println("successfully delete book.");
            } else {
                System.out.println("cancel!");
            }
        });
    }

    @FXML
    private void backToBooksManagementView() {
        BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW);
    }

    private void configureListReviews() {
        System.out.println("Configuring review list");
        reviewsList.setCellFactory(param -> new ListCell<>() {
            private final ImageView avatarImageView = new ImageView();
            private final ImageView ratingImageView = new ImageView();
            private final Text reviewText = new Text();
            private final VBox contentBox = new VBox(ratingImageView, reviewText);
            {
                ratingImageView.setFitHeight(20);
            }
            private final HBox cellContainer = new HBox(avatarImageView, contentBox);

            {
                avatarImageView.setFitHeight(20);
                avatarImageView.setFitWidth(20);
                cellContainer.setSpacing(5);
            }

            @Override
            protected void updateItem(Review review, boolean empty) {
                super.updateItem(review, empty);
                if (empty || review == null) {
                    setGraphic(null);
                } else {
                    reviewText.setText(review.getReview());
                    ratingImageView.setImage(new Image(getRatingImagePath(review.getRating())));
                    //avatarImageView.setImage(new Image(review.getAvatarPath(), true));
                    setGraphic(cellContainer);
                }
            }
        });
    }

    private String getRatingImagePath(int rating) {
        return switch (rating) {
            case 1 -> "file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/oneFillingsStar.png";
            case 2 -> "file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/twoFillingStars.png";
            case 3 -> "file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/threeFillingsStars.png";
            case 4 -> "file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/fourFillingsStars.png";
            case 5 -> "file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/fiveFillingsStars.png";
            default -> "file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/zero";
        };
    }

    private static int isInteger(String input) {
        if (input == null) return -1;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static BigDecimal isDecimal(String input) {
        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
