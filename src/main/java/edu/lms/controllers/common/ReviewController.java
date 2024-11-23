package edu.lms.controllers.common;

import edu.lms.models.book.Book;
import edu.lms.models.review.Review;
import edu.lms.models.review.ReviewCell;
import edu.lms.services.database.ReviewDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ReviewController {
    @FXML
    protected ListView<Review> reviewsList;

    protected ObservableList<Review> reviews;

    @FXML
    protected Label ratingLabel;

    @FXML
    protected Tooltip ratingTooltip;

    @FXML
    protected Label haveToVoteBookWarning;

    @FXML
    protected HBox starContainer;

    @FXML
    protected TextArea commentArea;

    @FXML
    protected Tooltip cannotModifyReview;

    protected Label[] stars;

    protected int currentRating;

    protected Book book;
    protected int userId;
    private boolean isVoted;

    protected void initialize(Book book, int userId) {
        this.book = book;
        this.userId = userId;
        reviews = ReviewDao.loadReviewsOfSpecificBook(book.getBookId());
        initializeVoting();
        configureListReviews();
        ratingLabel.setText(ReviewCell.loadRating(book.getRating().intValue()));
        ratingLabel.setTextFill(Color.GOLD);
        initializeTooltips();
        isVoted = ReviewDao.hasUserVoted(userId, book.getBookId());
        if (isVoted) commentArea.setDisable(true);
    }

    private void initializeVoting() {
        stars = new Label[5];
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

    private void initializeTooltips() {
        ratingTooltip.setText(book.getRating().toString());
        ratingTooltip.setStyle("-fx-font-size: 14;");
        ratingTooltip.setShowDelay(Duration.millis(500));
        Tooltip.install(ratingLabel, ratingTooltip);

        cannotModifyReview.setText("Be carefully, you can not modify your review!");
        cannotModifyReview.setStyle("-fx-font-size: 14;");
        cannotModifyReview.setShowDelay(Duration.millis(500));
        Tooltip.install(commentArea, cannotModifyReview);
    }

    private void configureListReviews() {
        System.out.println("Configuring review list");
        reviewsList.setCellFactory(param -> new ReviewCell(userId));
        reviewsList.setItems(reviews);
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

    @FXML
    protected void insertReviewIntoDatabase() {
        if (isVoted) return;

        String opinion = commentArea.getText();
        if (currentRating == 0 || opinion == null) {
            haveToVoteBookWarning.setVisible(true);
            return;
        }
        haveToVoteBookWarning.setVisible(false);
        Review newReview = new Review(userId, book.getBookId(), opinion, currentRating);
        ReviewDao.addReview(newReview);
        reviews.add(newReview);
        isVoted = true;
        commentArea.clear();
        commentArea.setDisable(true);
    }

}
