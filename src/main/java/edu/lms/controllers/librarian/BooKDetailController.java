package edu.lms.controllers.librarian;

import edu.lms.Constants;
import edu.lms.controllers.common.ReviewController;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.services.AlertDialog;
import edu.lms.services.database.BookDao;
import edu.lms.services.database.ReviewDao;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.math.BigDecimal;


public class BooKDetailController extends ReviewController {
    @FXML
    private Label authorsLabel;

    @FXML
    private TextField availableCopiesField;

    @FXML
    private Label availableCopiesLabel;

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
    private ImageView thumbnail;

    @FXML
    private TextField totalCopiesField;

    @FXML
    private Label totalCopiesLabel;

    @FXML
    private Label successfullyUpdateLabel;

    //file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/default_avatar.png

    public void initialize(Book book) {
        super.initialize(book, DashboardController.librarian.getId());
        System.out.println("initialize book detail.");
        initializeBookData(book);
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
        reviewsList.setItems(ReviewDao.loadReviewsOfSpecificBook(book.getBookId()));
        thumbnail.setImage(new Image(book.getCoverImage()));
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
        if (!BookDao.setTotalCopiesOfSpecificBook(book.getBookId(), value)) {
            System.err.println("Error can not set total copies.");
            return;
        }
        int adjustmentAvailableCopies = value - book.getTotalCopies();
        BookDao.updateAvailableCopiesOfThisBook(book.getBookId(), adjustmentAvailableCopies);
        BookDao.setTotalCopiesOfSpecificBook(book.getBookId(), value);
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
        if (!BookDao.setAvailableCopiesOfSpecificBook(book.getBookId(), value)) {
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
        if (!BookDao.setPriceOfSpecificBook(book.getBookId(), value)) {
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
                if (!BookDao.removeBook(book.getBookId())) {
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

    private void showSuccessfulUpdatingMessage() {
        successfullyUpdateLabel.setVisible(true);
        PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
        hideLabel.setOnFinished(e -> successfullyUpdateLabel.setVisible(false));
        hideLabel.play();
    }

    @FXML
    private void backToBooksManagementView() {
        BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW, true);
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
