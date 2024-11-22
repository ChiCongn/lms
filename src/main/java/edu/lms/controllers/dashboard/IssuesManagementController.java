package edu.lms.controllers.dashboard;

import edu.lms.models.issue.Issue;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.issue.IssuesManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class IssuesManagementController extends DashboardController implements Initializable {

    @FXML
    private TableView<BorrowedBook> borrowedBooksTableView;

    @FXML
    private TableColumn<BorrowedBook, Integer> borrowIdColumn;

    @FXML
    private TableColumn<BorrowedBook, String> borrowedBookColumn;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> borrowedDateColumn;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> dueDateColumn;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> returnDateColumn;

    @FXML
    private TableColumn<BorrowedBook, String> borrowerColumn;

    @FXML
    private TableColumn<BorrowedBook, BigDecimal> finesColumn;

    @FXML
    private TableView<Issue> issuesTableView;

    @FXML
    private TableColumn<Issue, String> issueIdColumn;

    @FXML
    private TableColumn<Issue, String> issueBookColumn;

    @FXML
    private TableColumn<Issue, String> overviewIssueColumn;

    @FXML
    private TableColumn<Issue, String> reporterColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<BorrowedBook> borrowedBooks = IssuesManager.getBorrowedBooks();
        borrowIdColumn.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        //borrowerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getUsername()));
        borrowedBookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        finesColumn.setCellValueFactory(new PropertyValueFactory<>("fines"));
        borrowedBooksTableView.setItems(borrowedBooks);

        ObservableList<Issue> issues = IssuesManager.getIssues();
        issueIdColumn.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReporter().getUsername()));
        issueBookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIssueBook().getTitle()));
        overviewIssueColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        issuesTableView.setItems(issues);
    }
}
