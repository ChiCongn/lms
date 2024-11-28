package edu.lms.controllers.librarian;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.book.VerticalCard;
import edu.lms.models.issue.IssuesManager;
import edu.lms.models.user.UserManager;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class LibrarianDashboardController extends DashboardController implements Initializable {
    @FXML
    private Label numberOfBooksLabel;

    @FXML
    private Label numberOfBorrowedBooksLabel;

    @FXML
    private Label numberOfClientsLabel;

    @FXML
    private HBox topChoiceBooksContainer;

    @FXML
    private BarChart<String, Number> borrowedBooksBarChart;

    @FXML
    private PieChart categoriesPieChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(librarian.getUsername());
        avatarImage.setImage(new Image(librarian.getAvatarPath()));
        numberOfBooksLabel.setText(Integer.toString(BookManager.getNumberOfBooks()));
        numberOfBorrowedBooksLabel.setText(Integer.toString(IssuesManager.getTotalBorrowedBook()));
        numberOfClientsLabel.setText(Integer.toString(UserManager.getNumberOfClients()));

        initializeMonthlyBorrowedChart();
        initializeCategoriesDistributionPieChart();

        List<Book> topChoiceBooks = BookManager.getTopChoiceBooks();
        System.out.println("set up top choice books");
        setUpTopChoiceBooksData(topChoiceBooks);
        topChoiceBooksContainer.setPrefWidth(1040);
    }

    private void switchToBookDetails(Book book) {
        BooKDetailController booKDetailController = SceneManager.switchScene(Constants.BOOK_DETAILS_VIEW, true);
        assert booKDetailController != null;
        booKDetailController.initialize(book);
    }

    private void initializeMonthlyBorrowedChart() {
        System.out.println("set up monthly borrowed chart");
        Map<String, Integer> borrowedBooksData = BookManager.getBorrowedBooksDataByMonth();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Borrowed Books");

        borrowedBooksData.forEach((month, count) -> {
            series.getData().add(new XYChart.Data<>(month, count));
        });

        borrowedBooksBarChart.getData().clear();
        borrowedBooksBarChart.getData().add(series);
    }

    private void initializeCategoriesDistributionPieChart() {
        System.out.println("set up category distribution chart");
        ObservableList<PieChart.Data> categoriesDistributionData = BookManager.getCategoriesDistributionData();

        categoriesPieChart.setData(categoriesDistributionData);

        categoriesPieChart.setTitle("Category Distribution");

        System.out.println("set up tooltip for better UX");
        categoriesDistributionData.forEach(data -> {
            Tooltip tooltip = new Tooltip(data.getName());
            Tooltip.install(data.getNode(), tooltip);
        });
    }

    private void setUpTopChoiceBooksData(List<Book> topChoiceBooks) {
        Task<Void> loadTopChoiceBooksTask = new Task<>() {
            @Override
            protected Void call() {
                for (Book book : topChoiceBooks) {

                    VBox bookCard = new VerticalCard(book);
                    bookCard.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            switchToBookDetails(book);
                        }
                    });

                    // Updating the UI safely on the JavaFX Application Thread
                    Platform.runLater(() -> topChoiceBooksContainer.getChildren().add(bookCard));
                }
                return null;
            }
        };

        loadTopChoiceBooksTask.setOnSucceeded(e -> System.out.println("Books loaded successfully!"));
        loadTopChoiceBooksTask.setOnFailed(e -> System.err.println("Error loading books: " + loadTopChoiceBooksTask.getException().getMessage()));

        new Thread(loadTopChoiceBooksTask).start();
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
