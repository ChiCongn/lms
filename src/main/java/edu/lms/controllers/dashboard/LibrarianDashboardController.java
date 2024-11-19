package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.login.SignInController;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.Librarian;
import edu.lms.models.user.UserManager;
import edu.lms.services.AlertDialog;
import edu.lms.services.Config;
import edu.lms.services.GoogleBooksAPI;
import edu.lms.services.database.BorrowedBookDataService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianDashboardController implements Initializable {
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
    private ImageView avatar;

    private Librarian librarian;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfBooksLabel.setText(Integer.toString(BookManager.getNumberOfBooks()));
        numberOfBorrowedBooksLabel.setText(Integer.toString(BorrowedBookDataService.getNumberOfBorrowedBook()));
        numberOfClientsLabel.setText(Integer.toString(UserManager.getNumberOfClients()));
    }

    public void setData(Librarian librarian) {
        this.librarian = librarian;
        usernameLabel.setText(librarian.getUsername());
        avatar = new ImageView(librarian.getAvatarPath());
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }


    /*@FXML
        public void searchBook(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                System.out.println("search :(");

                String query = searchText.getText();
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
                private final Text titleText = new Text();
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
        }

        @FXML
        private void setInvisibleSearchResult() {
            searchResult.setVisible(false);
        }
    */
    @FXML
    private void showConfirmationAlert() {
        Alert confirmation = AlertDialog.makeConfirmationAlert("test", "click ok :)");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("ok confirm");
            } else {
                System.out.println("cancel");
            }
        });
    }

    @FXML
    private void switchToClientManagementView() {
        ClientsManagementController clientsManagementController = SceneManager.switchScene(Constants.CLIENT_MANAGEMENT_VIEW);
    }

    @FXML
    private void switchToBooksManagementView() {
        BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW);
    }

    @FXML
    private void logout() {
        SignInController signInController = SceneManager.switchScene(Constants.SIGN_IN_VIEW);
    }
}
