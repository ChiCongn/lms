package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.book.Card;
import edu.lms.models.user.ClientDataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientDashboardController extends DashboardController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView avatarImage;

    @FXML
    private HBox topChoiceBooksContainer;

    @FXML
    private HBox recentBooksContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(client.getUsername());
        avatarImage.setImage(new Image(client.getAvatarPath()));
        initializeTopChoiceBooks();
        initializeRecentBooks();
    }

    private void initializeRecentBooks() {
        List<Book> recentBooks = ClientDataManager.getRecentBooks();
        System.out.println("set up recent books");
        for (int i = 0; i < recentBooks.size(); i++) {
            final int currentIndex = i;
            VBox bookCard = createBookCard(recentBooks.get(currentIndex));
            bookCard.setOnMouseClicked(mouseEvent -> {
                ClientBookDetailsController clientBookDetailsController = SceneManager.switchScene(Constants.CLIENT_BOOK_DETAILS_VIEW, true);
                assert clientBookDetailsController != null;
                clientBookDetailsController.initialize(recentBooks.get(currentIndex));
            });
            recentBooksContainer.getChildren().add(bookCard);
        }
    }

    private void initializeTopChoiceBooks() {
        List<Book> topChoiceBooks = BookManager.getTopChoiceBooks();
        System.out.println("set up top choice books");
        for (int i = 0; i < 10; i++) {
            final int currentIndex = i;

            Card bookCard = new Card(topChoiceBooks.get(currentIndex));
            topChoiceBooksContainer.getChildren().add(bookCard);
        }
    }

    private VBox createBookCard(Book book) {
        ImageView bookCover = new ImageView(book.getThumbnail());
        if (book.getThumbnail() == null) System.out.println("thumbnail is null");
        bookCover.setFitWidth(150);
        bookCover.setFitHeight(150);

        Label bookTitle = new Label(book.getTitle());
        bookTitle.setWrapText(true);
        bookTitle.prefWidth(150);
        bookTitle.setTextAlignment(TextAlignment.CENTER);

        VBox bookCard = new VBox(5, bookCover, bookTitle); // 5px spacing between image and title
        bookCard.setStyle("-fx-alignment: center; -fx-border-color: lightgray; -fx-padding: 15;");

        return bookCard;
    }
}
