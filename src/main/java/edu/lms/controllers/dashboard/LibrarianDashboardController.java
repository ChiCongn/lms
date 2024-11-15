package edu.lms.controllers.dashboard;

import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.Librarian;
import edu.lms.models.user.UserManager;
import edu.lms.services.AlertDialog;
import edu.lms.services.GoogleBooksAPI;
import edu.lms.services.database.BorrowedBookDataService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    private Label numOfClients;
    @FXML
    private Label numOfBooks;
    @FXML
    private Label numOfBorrowedBooks;
    @FXML
    private Label username;
    @FXML
    private ImageView addBookImage;

    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, Integer> titleColumn;
    @FXML
    private TableColumn<Book, Integer> totalCopiesColumn;
    @FXML
    private TableColumn<Book, Integer> availableCopiesColumn;
    @FXML
    private TableColumn<Book, String> authorsColumn;


    @FXML
    private TableView<Client> clientsTableView;
    @FXML
    private TableColumn<Client, Integer> idUserIdColumn;
    @FXML
    private TableColumn<Client, String> usernameColumn;
    @FXML
    private TableColumn<Client, Integer> borrowedBooksColumn;
    @FXML
    private TableColumn<Client, BigDecimal> outstandingFinesColumn;


    @FXML
    public TextField searchText;
    @FXML
    private ListView<Book> searchResult;

    private Librarian librarian;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        librarian = new Librarian("quynhchi", "Qunhchi@12", "quynhchi08@gmail.com");
        Tooltip tooltip = new Tooltip("Add a new book");
        Tooltip.install(addBookImage, tooltip);

        ObservableList<Book> books = BookManager.getBooks();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        totalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCopies"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        bookTableView.setItems(books);
        bookTableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    switchToBookDetail(selectedBook);
                }
            }
        });

        ObservableList<Client> clients = UserManager.getClients();
        idUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        borrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedBooksCount"));
        outstandingFinesColumn.setCellValueFactory(new PropertyValueFactory<>("outstandingFines"));
        clientsTableView.setItems(clients);

        numOfBooks.setText(Integer.toString(books.size()));
        numOfClients.setText(Integer.toString(clients.size()));
        numOfBorrowedBooks.setText(Integer.toString(BorrowedBookDataService.getNumberOfBorrowedBook()));

        System.out.println("reload data in librarian dashboard");
        clientsTableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Client selectedUser = clientsTableView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    switchToClientDetail(selectedUser);
                }
            }
        });

        configureListView();
    }

    private void switchToClientDetail(Client client) {
        ClientDetailsController clientDetailsController = SceneManager.switchScene("/edu/lms/fxml/user-details.fxml");
        assert clientDetailsController != null;
        clientDetailsController.setClientDetails(client);
    }

    private void switchToBookDetail(Book book) {
        BooKDetailController booKDetailController = SceneManager.switchScene("/edu/lms/fxml/book-detail-view.fxml");
        assert booKDetailController != null;
        booKDetailController.initialize(book, librarian);
    }

    @FXML
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
