package edu.lms.controllers.dashboard;

import edu.lms.models.book.Book;
import edu.lms.models.book.Review;
import edu.lms.models.user.User;
import edu.lms.services.database.ReviewDataService;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class BooKDetailController {
    @FXML
    private ListView<Review> reviews;
    @FXML
    private ImageView avatar;
    @FXML
    private Button sendButton;
    private Book book;
    private User user;


    public void initialize(Book book, User user) {
        configureListReviews();
        System.out.println("initialize book detail");
        this.book = book;
        this.user = user;
        reviews.setItems(ReviewDataService.loadReviewsOfSpecificBook(book.getBookId()));
        //avatar = new ImageView(user.getAvatarPath());
    }

    private void configureListReviews() {
        System.out.println("Configuring review list");
        reviews.setCellFactory(param -> new ListCell<>() {
            private final ImageView avatarImageView = new ImageView();
            private final ImageView ratingImageView = new ImageView();
            private final Text reviewText = new Text();
            private final VBox contentBox = new VBox(ratingImageView, reviewText);
            {
                ratingImageView.setFitHeight(20);
            }
            private final HBox cellContainer = new HBox(avatarImageView, contentBox);

            {
                avatarImageView.setFitHeight(20);
                avatarImageView.setFitWidth(20);
                cellContainer.setSpacing(5);
            }

            @Override
            protected void updateItem(Review review, boolean empty) {
                super.updateItem(review, empty);
                if (empty || review == null) {
                    setGraphic(null);
                } else {
                    reviewText.setText(review.getReview());
                    String ratingImagePath = getClass().getResource(getRatingImagePath(review.getRating())).toString();
                    ratingImageView.setImage(new Image(ratingImagePath));
                    //avatarImageView.setImage(new Image(review.getAvatarPath(), true));
                    setGraphic(cellContainer);
                }
            }
        });
    }

    //edu/lms/images/oneFillingStar.png
    private String getRatingImagePath(int rating) {
        return switch (rating) {
            case 1 -> "edu/lms/images/oneFillingsStar.png";
            case 2 -> "/edu/lms/images/twoFillingStars.png";
            case 3 -> "edu/lms/images/threeFillingsStars.png";
            case 4 -> "edu/lms/images/fourFillingsStars.png";
            case 5 -> "edu/lms/images/fiveFillingsStars.png";
            default -> "edu/lms/images/zero";
        };
    }

    public ListView<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ListView<Review> reviews) {
        this.reviews = reviews;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public void setSendButton(Button sendButton) {
        this.sendButton = sendButton;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
