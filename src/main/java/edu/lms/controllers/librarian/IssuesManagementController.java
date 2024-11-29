package edu.lms.controllers.librarian;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.issue.Issue;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.issue.IssuesManager;
import edu.lms.models.user.UserManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
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

    @FXML
    private BarChart<String, Number> issuesBarChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBorrowedBooksTable();
        initializeIssueBooksTable();
        initializeMonthlyIssuesChart();
    }

    private void initializeIssueBooksTable() {
        ObservableList<Issue> issues = IssuesManager.getIssues();
        issueIdColumn.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                Objects.requireNonNull(UserManager.getClient(cellData.getValue().getReporterId())).getUsername()));
        issueBookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIssueBook().getTitle()));
        overviewIssueColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        issuesTableView.setItems(issues);
        /*issuesTableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                Issue selectedIssue = issuesTableView.getSelectionModel().getSelectedItem();
                if (selectedIssue != null) {
                    //switchToIssueDetail(selectedIssue);
                }
            }
        });*/
    }

    private void initializeBorrowedBooksTable() {
        ObservableList<BorrowedBook> borrowedBooks = IssuesManager.getBorrowedBooks();
        borrowIdColumn.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        borrowerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Objects.requireNonNull(
                UserManager.getClient(cellData.getValue().getClientId())).getUsername()));
        borrowedBookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        finesColumn.setCellValueFactory(new PropertyValueFactory<>("fines"));
        borrowedBooksTableView.setItems(borrowedBooks);
    }

    private void initializeMonthlyIssuesChart() {
        System.out.println("set up monthly issue books chart");
        Map<String, Integer> issues = BookManager.getIssuesDataByMonth();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly issues");

        issues.forEach((month, count) -> {
            series.getData().add(new XYChart.Data<>(month, count));
        });

        issuesBarChart.getData().clear();
        issuesBarChart.getData().add(series);
    }

    private void switchToIssueDetail(Issue selectedIssue) {
        IssueDetailsController issueDetailsController = SceneManager.switchScene(Constants.ISSUE_DETAIL_VIEW, true);
        assert issueDetailsController != null;
        issueDetailsController.initialize(selectedIssue);
    }
}
