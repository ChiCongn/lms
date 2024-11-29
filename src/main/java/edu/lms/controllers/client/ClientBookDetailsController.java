package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.common.ReviewController;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.ClientDataManager;

import edu.lms.services.AlertDialog;
import edu.lms.services.database.BorrowedBookDao;
import edu.lms.services.database.ClientDao;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;



public class ClientBookDetailsController extends ReviewController {

    @FXML
    private Label authorsLabel;

    @FXML
    private Label categoriesLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label languageLabel;

    @FXML
    private Label pageCountLabel;

    @FXML
    private Label publishedYearLabel;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Label favourite;

    @FXML
    private Button borrow;

    private boolean isFavouriteBook;
    private boolean isBorrowed;

    private int clientId;

    private String previousScene;

    private Book book;


    public void initialize(Book book, int clientId, String previousScene) {
        System.out.println("initialize book detail.");
        this.book = book;
        this.clientId = clientId;
        this.previousScene = previousScene;
        initializeBookData(book);
        favourite.setText(isFavouriteBook(book.getBookId()));
        super.initialize(book, clientId);
        isBorrowed = BorrowedBookDao.isBorrowedByThisClient(book.getBookId(), clientId);
        borrow.setText(isBorrowedBook());
        borrow.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                toggleBorrow();
            }
        });
    }

    private void initializeBookData(Book book) {
        authorsLabel.setText(book.getAuthors());
        pageCountLabel.setText(Integer.toString(book.getPageCount()));
        publishedYearLabel.setText(book.getPublishedYear());
        languageLabel.setText(book.getLanguage());
        categoriesLabel.setText(book.getCategories());
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setText(book.getDescription());
        descriptionTextArea.setEditable(false);
        thumbnail.setImage(new Image(book.getCoverImage()));
    }

    @FXML
    private void backToPreviousView() {
        updateDataBeforeLeaving();
        if (previousScene.equals("search books")) {
            ClientSearchBookController controller = SceneManager.switchScene(Constants.CLIENT_SEARCH_BOOK_VIEW, true);
        } else if (previousScene.equals("categories")) {
            CategoriesController categoriesController = SceneManager.switchScene(Constants.CATEGORIES_VIEW, true);
        } else if (previousScene.equals("borrowed books")) {
            BorrowedBooksController borrowedBooksController = SceneManager.switchScene(Constants.CLIENT_BORROWED_BOOKS_VIEW, true);
        } else if (previousScene.equals("favourite books")) {
            FavouriteBooksController favouriteBooksController = SceneManager.switchScene(Constants.FAVOURITE_DASHBOARD_VIEW, true);
        } else {
            ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW, true);
        }
    }

    private void updateDataBeforeLeaving() {
        if (isFavouriteBook) {
            if (ClientDataManager.addFavouriteBook(book)) {
                ClientDao.addFavouriteBook(clientId, book.getBookId());
            }
        } else {
            ClientDao.unfavouriteBook(clientId, book.getBookId());
            ClientDataManager.unfavouriteBook(book);
        }
        if (ClientDataManager.addRecentBook(book)) {
            ClientDao.addRecentBook(clientId, book.getBookId());
        }
        if (isBorrowed) {
            borrowBook();
        } else {
            BorrowedBookDao.markBookAsReturned(clientId, book.getBookId());
        }
    }

    @FXML
    private void toggleFavourite() {
        isFavouriteBook = !isFavouriteBook;
        if (isFavouriteBook) {
            favourite.setText("★");
            //ClientDao.addFavouriteBook(clientId, book.getBookId());
        } else {
            favourite.setText("☆");
            //ClientDao.unfavouriteBook(clientId, book.getBookId());
        }
    }

    @FXML
    private void toggleBorrow() {
        isBorrowed = !isBorrowed;
        if (isBorrowed) {
            Alert confirmation = AlertDialog.makeConfirmationAlert("confirmation",
                    "Warning: Are you want to borrow this book?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    borrow.setText("Return now");
                } else {
                    System.out.println("cancel");
                }
            });
        } else {

            Alert confirmation = AlertDialog.makeConfirmationAlert("confirmation",
                    "Warning: Are you want to return this book?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    isBorrowed = false;
                    borrow.setText("Borrow now");
                    System.out.println("return book");
                } else {
                    System.out.println("cancel");
                }
            });
        }
    }

    private void borrowBook() {
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(Constants.DEFAULT_BORROW_DURATION_DAYS);

        //Book book, int clientId, LocalDate borrowDate, LocalDate dueDate, String status
        BorrowedBook borrowedBook = new BorrowedBook(book, clientId, borrowDate,dueDate, "borrowed");
        BorrowedBookDao.addNewBorrowedBook(borrowedBook);
        isBorrowed = true;
        System.out.println("borrow book");
    }

    private void returnBook() {

    }

    @FXML
    private void openBook() {
        String url = book.getCanonicalVolumeLink();
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String isFavouriteBook(int bookId) {
        if (ClientDataManager.isFavouriteBook(bookId)) {
            isFavouriteBook = true;
            return "★";
        } else {
            return "☆";
        }
    }

    private String isBorrowedBook() {
        if (isBorrowed) return "Return now";
        else return "Borrow now";
    }
}
