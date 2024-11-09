package edu.lms.controllers.dashboard;

import edu.lms.models.book.Book;
import edu.lms.services.database.BookDataService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianDashboardController implements Initializable {
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
    private static ObservableList<Book> books;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        books = BookDataService.loadBooksData();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        totalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCopies"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        bookTableView.setItems(books);
    }
}
