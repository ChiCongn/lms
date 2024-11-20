package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.user.UserManager;

import edu.lms.services.AlertDialog;
import edu.lms.services.database.BookDataService;
import edu.lms.services.database.BorrowedBookDataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.List;
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
    private ImageView avatarImage;

    @FXML
    private HBox booksContainer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(librarian.getUsername());
        avatarImage.setImage(new Image(librarian.getAvatarPath()));
        numberOfBooksLabel.setText(Integer.toString(BookManager.getNumberOfBooks()));
        numberOfBorrowedBooksLabel.setText(Integer.toString(BorrowedBookDataService.getNumberOfBorrowedBook()));
        numberOfClientsLabel.setText(Integer.toString(UserManager.getNumberOfClients()));
        List<Book> topChoiceBooks = BookDataService.loadTopChoicesBook();

        for (int i = 0; i < 20; i++) {
            final int currentIndex = i;
            VBox bookCard = createBookCard(topChoiceBooks.get(currentIndex));
            bookCard.setOnMouseClicked(mouseEvent -> {
                BooKDetailController booKDetailController = SceneManager.switchScene(Constants.BOOK_DETAILS_VIEW);
                assert booKDetailController != null;
                booKDetailController.initialize(topChoiceBooks.get(currentIndex));
            });
            booksContainer.getChildren().add(bookCard);
        }

    }

    private VBox createBookCard(Book book) {
        ImageView bookCover = new ImageView(new Image(book.getCoverImage()));
        bookCover.setFitWidth(100);
        bookCover.setFitHeight(150);

        Label bookTitle = new Label(book.getTitle());
        bookTitle.setWrapText(true);
        bookTitle.prefWidth(100);
        bookTitle.setTextAlignment(TextAlignment.CENTER);

        VBox bookCard = new VBox(5, bookCover, bookTitle); // 5px spacing between image and title
        bookCard.setStyle("-fx-alignment: center; -fx-border-color: lightgray; -fx-padding: 10;");

        return bookCard;
    }

}
