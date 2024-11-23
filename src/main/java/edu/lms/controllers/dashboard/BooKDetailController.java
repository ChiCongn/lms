package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.review.Review;
import edu.lms.services.AlertDialog;
import edu.lms.services.database.BookDataService;
import edu.lms.services.database.ReviewDataService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    private ListView<Review> reviewsList;

    @FXML
    private ImageView thumbnail;

    @FXML
    private TextField totalCopiesField;

    @FXML
    private Label totalCopiesLabel;

    @FXML
    private Label successfullyUpdateLabel;

    //file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/default_avatar.png

    @FXML
    private Label ratingLabel;

    @FXML
    private Tooltip ratingTooltip;

    @FXML
    private HBox starContainer;

    @FXML
    private Label haveToVoteBookWarning;

    private Label[] stars;

    private int currentRating;

    private Book book;


    public void initialize(Book book) {
        System.out.println("initialize book detail.");
        configureListReviews();
        this.book = book;

        initializeBookData(book);

        // set up voting area for voting and review.
        stars = new Label[5];
        System.out.println("set up voting area.");
        for (int i = 0; i < 5; i++) {
            Label star = new Label("☆");
            star.setFont(new Font("Arial", 22));
            star.setTextFill(Color.GRAY);
            int starIndex = i;

            star.setOnMouseClicked(event -> updateStars(starIndex + 1));

            stars[i] = star;
            starContainer.getChildren().add(star);
        }

        totalCopiesField.setOnKeyReleased(keyEvent -> {
            isValidTotalCopies();
        });

    }

    private void initializeBookData(Book book) {
        authorsLabel.setText(book.getAuthors());
        pageCountLabel.setText(Integer.toString(book.getPageCount()));
        totalCopiesLabel.setText(Integer.toString(book.getTotalCopies()));
        availableCopiesLabel.setText(Integer.toString(book.getAvailableCopies()));
        priceLabel.setText(book.getPrice().toString());
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setText(book.getDescription());
        descriptionTextArea.setEditable(false);
        reviewsList.setItems(ReviewDataService.loadReviewsOfSpecificBook(book.getBookId()));
        thumbnail.setImage(book.getThumbnail());
        ratingLabel.setText(loadRating());
        ratingTooltip.setText(book.getRating().toString());
        ratingTooltip.setStyle("-fx-font-size: 14;");
        ratingTooltip.setShowDelay(Duration.millis(500));
        Tooltip.install(ratingLabel, ratingTooltip);
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
        BookDataService.setTotalCopiesOfSpecificBook(book.getBookId(), value);
        showSuccessfulUpdatingMessage();
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
        showSuccessfulUpdatingMessage();
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
        showSuccessfulUpdatingMessage();
        book.setPrice(value);
    }

    @FXML
    private void updateAll() {
        setAvailableCopies();
        setTotalCopies();
        setPriceBook();
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
                BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW, true);
                assert booksManagementController != null;
                booksManagementController.deleteBook(book);
                BookManager.deleteBook(book);
                System.out.println("successfully delete book.");
            } else {
                System.out.println("cancel!");
            }
        });
    }

    private String loadRating() {
        System.out.println("rating of this book is: " + book.getRating().intValue());
        return switch (book.getRating().intValue()) {
            case 1 -> "★☆☆☆☆";
            case 2 -> "★★☆☆☆";
            case 3 -> "★★★☆☆";
            case 4 -> "★★★★☆";
            case 5 -> "★★★★★";
            default -> "☆☆☆☆☆";
        };
    }

    private void showSuccessfulUpdatingMessage() {
        successfullyUpdateLabel.setVisible(true);
        PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
        hideLabel.setOnFinished(e -> successfullyUpdateLabel.setVisible(false));
        hideLabel.play();
    }

    private void updateStars(int rating) {
        this.currentRating = rating;

        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars[i].setText("★");
                stars[i].setTextFill(Color.GOLD);
            } else {
                stars[i].setText("☆");
                stars[i].setTextFill(Color.GRAY);
            }
        }
    }

    private void insertReviewIntoDatabase() {
        String opinion = commentArea.getText();
        if (currentRating == 0 || opinion == null) {
            haveToVoteBookWarning.setVisible(true);
        }
        haveToVoteBookWarning.setVisible(false);
        //Review review = new Review(1, book.getBookId(), opinion);
    }

    @FXML
    private void backToBooksManagementView() {
        BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW, true);
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
