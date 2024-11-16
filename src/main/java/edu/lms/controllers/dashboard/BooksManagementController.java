package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.Librarian;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BooksManagementController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView avatar;
    @FXML
    private TextField searchFilter;
    @FXML
    private ImageView magnifyingGlass;
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    //private TableColumn<Book, HBox> titleColumn;   lag quazz ko choi duoc :(
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorsColumn;
    @FXML
    private TableColumn<Book, Integer> totalCopiesColumn;
    @FXML
    private TableColumn<Book, Integer> availableCopiesColumn;

    private Librarian librarian;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("load all books for books management view");
        ObservableList<Book> books = BookManager.getBooks();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        /*titleColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setGraphic(null);
                } else {
                    Book book = getTableRow().getItem();

                    if (book != null) {
                        ImageView avatar = new ImageView(new Image(book.getCoverImage()));
                        avatar.setFitWidth(20);
                        avatar.setFitHeight(20);
                        Label username = new Label(book.getTitle());

                        // Style HBox
                        HBox hbox = new HBox(10, avatar, username);
                        hbox.setAlignment(Pos.CENTER_LEFT);

                        setGraphic(hbox);
                    }
                }
            }
        });*/ // lag lam :(
        totalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCopies"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        bookTableView.setItems(books);
        bookTableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    switchToBookDetails(selectedBook);
                }
            }
        });
    }

    //transfer librarian data from another scene to this scene.
    public void setData(Librarian librarian) {
        this.librarian = librarian;
        usernameLabel.setText(librarian.getUsername());
        avatar.setImage(new Image(librarian.getAvatarPath()));
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    private void switchToBookDetails(Book book) {
        BooKDetailController booKDetailController = SceneManager.switchScene(Constants.BOOK_DETAILS_VIEW);
        assert booKDetailController != null;
        booKDetailController.initialize(book, librarian);
    }
}
