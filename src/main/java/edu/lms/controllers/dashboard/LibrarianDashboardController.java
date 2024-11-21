package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.Client.CardDetailsController;
import edu.lms.models.book.BookManager;

import edu.lms.models.user.Client;
import edu.lms.models.user.Librarian;
import edu.lms.models.user.User;
import edu.lms.models.user.UserManager;

import edu.lms.services.AlertDialog;
import edu.lms.services.database.BorrowedBookDataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
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
    private ImageView avatar;

    @FXML
    private GridPane Container;

    @FXML
    private GridPane Container1;

    @FXML
    private GridPane Container2;

    Constants constants = new Constants();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //usernameLabel.setText(librarian.getUsername());
//        avatar = new ImageView(librarian.getAvatarPath());
//        numberOfBooksLabel.setText(Integer.toString(BookManager.getNumberOfBooks()));
//        numberOfBorrowedBooksLabel.setText(Integer.toString(BorrowedBookDataService.getNumberOfBorrowedBook()));
//        numberOfClientsLabel.setText(Integer.toString(UserManager.getNumberOfClients()));


        Constants.recommended = constants.books();
        Constants.users = constants.getUsers();

        int column = 0;
        int row = 1;


        for (edu.lms.models.book.Book value : Constants.recommended) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/edu/lms/fxml/book-box.fxml"));
            HBox bookBox = null;
            try {
                bookBox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BookBoxController bookController = fxmlLoader.getController();
            bookController.setData(value);


            if (column == 1) {
                column = 0;
                ++row;
            }

            Container.add(bookBox, column++, row);
            GridPane.setMargin(bookBox, new Insets(1));

            }

        for (Client value : Constants.users) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/edu/lms/fxml/user-box.fxml"));
            HBox bookBox = null;
            try {
                bookBox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            UserBoxController bookController = fxmlLoader.getController();
            bookController.setData(value);


            if (column == 1) {
                column = 0;
                ++row;
            }

            Container1.add(bookBox, column++, row);
            GridPane.setMargin(bookBox, new Insets(1));

        }

        for (Client value : Constants.users) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/edu/lms/fxml/user-box.fxml"));
            HBox bookBox = null;
            try {
                bookBox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            UserBoxController bookController = fxmlLoader.getController();
            bookController.setData(value);


            if (column == 1) {
                column = 0;
                ++row;
            }

            Container2.add(bookBox, column++, row);
            GridPane.setMargin(bookBox, new Insets(1));

        }

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

}
