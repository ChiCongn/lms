package edu.lms.controllers.librarian;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;

import edu.lms.models.issue.IssuesManager;
import edu.lms.models.search.BookSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class BooksManagementController extends DashboardController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView avatar;

    @FXML
    private TextField searchFilter;

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

    @FXML
    private Label totalBooksLabel;

    @FXML
    private Label totalBorrowedBooksLabel;

    @FXML
    private Label totalFinesLabel;

    @FXML
    private Label totalIssuesLabel;

    private ObservableList<Book> books;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(librarian.getUsername());
        avatarImage.setImage(new Image(librarian.getAvatarPath()));
        System.out.println("load all books for books management view");
        initializeBooksTableView();
        totalBooksLabel.setText(Integer.toString(BookManager.getNumberOfBooks()));
        totalIssuesLabel.setText(Integer.toString(IssuesManager.getNumberOfIssues()));
        totalFinesLabel.setText(IssuesManager.getTotalFines().toString());
        totalBorrowedBooksLabel.setText(Integer.toString(IssuesManager.getNumberOfBorrowed()));
    }

    private void initializeBooksTableView() {
        books = BookManager.getBooks();
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

    public void deleteBook(Book book) {
        bookTableView.getItems().remove(book);
        System.out.println("successfully delete book from table view");
    }

    @FXML
    private void switchToAddBookView() {
        AddBookController addBookController = SceneManager.switchScene(Constants.ADD_BOOK_VIEW, true);
    }

    @FXML
    private void handleSearch() {
        String filter = searchFilter.getText().toLowerCase();
        if (filter.isEmpty()) {
            bookTableView.setItems(books);
        } else {
            // Filter the books based on the search query
            // wow surprised! O(k logN) k is constant
            String[] keywords = filter.split("\\s+");
            Set<Integer> filteredBookId = BookSearch.searchBooksByKeywords(keywords, true);
            ObservableList<Book> filteredBooks = BookManager.getFilteredBooks(filteredBookId);

            // O(n*m)
            // n is the number of books.
            // m is the average length of the book titles.
            /*ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(filter)) {
                    filteredBooks.add(book);
                }
            }*/
            bookTableView.setItems(filteredBooks);
        }
    }

    private void switchToBookDetails(Book book) {
        BooKDetailController booKDetailController = SceneManager.switchScene(Constants.BOOK_DETAILS_VIEW, true);
        assert booKDetailController != null;
        booKDetailController.initialize(book);
    }

}
