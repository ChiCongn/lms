package edu.lms.controllers.dashboard;

import edu.lms.models.book.Book;
import edu.lms.models.user.Client;
import edu.lms.services.database.BookDataService;
import edu.lms.services.database.ClientDataService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
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


    @FXML
    private TableView<Client> usersTableView;
    @FXML
    private TableColumn<Client, Integer> idUserIdColumn;
    @FXML
    private TableColumn<Client, String> usernameColumn;
    @FXML
    private TableColumn<Client, Integer> borrowedBooksColumn;
    @FXML
    private TableColumn<Client, BigDecimal> outstandingFinesColumn;


    private static ObservableList<Book> books;
    private static ObservableList<Client> clients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clients = ClientDataService.loadClientsData();
        idUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        borrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedBooksCount"));
        outstandingFinesColumn.setCellValueFactory(new PropertyValueFactory<>("outstandingFines"));
        usersTableView.setItems(clients);

        books = BookDataService.loadBooksData();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        totalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCopies"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        bookTableView.setItems(books);
    }
}
