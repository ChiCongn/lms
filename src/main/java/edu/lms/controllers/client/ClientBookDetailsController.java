package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.review.Review;
import edu.lms.models.review.ReviewCell;
import edu.lms.services.database.ReviewDataService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class ClientBookDetailsController {

    @FXML
    private Label authorsLabel;

    @FXML
    private Label categoriesLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label languageLabel;

    @FXML
    private Label pageCountLabel;

    @FXML
    private Label publishedYearLabel;

    @FXML
    private ImageView thumbnail;

    @FXML
    private ListView<Review> reviewsList;

    @FXML
    private Label ratingLabel;

    @FXML
    private Tooltip ratingTooltip;

    @FXML
    private Label haveToVoteBookWarning;

    @FXML
    private HBox starContainer;

    @FXML
    private TextArea commentArea;

    private Label[] stars;

    private int currentRating;

    private Book book;


    public void initialize(Book book) {
        System.out.println("initialize book detail.");
        configureListReviews();
        this.book = book;

        initializeBookData(book);

        // set up voting area for voting and review.
        stars = new Label[5];
        System.out.println("set up voting area.");
        for (int i = 0; i < 5; i++) {
            Label star = new Label("☆");
            star.setFont(new Font("Arial", 22));
            star.setTextFill(Color.GRAY);
            int starIndex = i;

            star.setOnMouseClicked(event -> updateStars(starIndex + 1));

            stars[i] = star;
            starContainer.getChildren().add(star);
        }
    }

    private void initializeBookData(Book book) {
        authorsLabel.setText(book.getAuthors());
        pageCountLabel.setText(Integer.toString(book.getPageCount()));
        publishedYearLabel.setText(book.getPublishedYear());
        languageLabel.setText(book.getLanguage());
        categoriesLabel.setText(book.getCategories());
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setText(book.getDescription());
        descriptionTextArea.setEditable(false);
        reviewsList.setItems(ReviewDataService.loadReviewsOfSpecificBook(book.getBookId()));
        thumbnail.setImage(book.getThumbnail());
        ratingLabel.setText(ReviewCell.loadRating(book.getRating().intValue()));
        ratingTooltip.setText(book.getRating().toString());
        ratingTooltip.setStyle("-fx-font-size: 14;");
        ratingTooltip.setShowDelay(Duration.millis(500));
        Tooltip.install(ratingLabel, ratingTooltip);
    }

    private void configureListReviews() {
        System.out.println("Configuring review list");
        reviewsList.setCellFactory(param -> new ReviewCell());
    }

    private void updateStars(int rating) {
        this.currentRating = rating;

        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars[i].setText("★");
                stars[i].setTextFill(Color.GOLD);
            } else {
                stars[i].setText("☆");
                stars[i].setTextFill(Color.GRAY);
            }
        }
    }

    private void insertReviewIntoDatabase() {
        String opinion = commentArea.getText();
        if (currentRating == 0 || opinion == null) {
            haveToVoteBookWarning.setVisible(true);
            return;
        }
        haveToVoteBookWarning.setVisible(false);

    }

    @FXML
    private void backToClientDashboardViewView() {
        ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW, true);
    }
}
