package edu.lms.models.review;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ReviewCell extends ListCell<Review> {
    private final ImageView avatarImageView = new ImageView();
    private final Label ratingLabelOfOtherUser = new Label();
    private final Text reviewText = new Text();
    private final VBox contentBox = new VBox(ratingLabelOfOtherUser, reviewText);
    private final HBox cellContainer = new HBox();
    private final int userId;

    public ReviewCell(int userId) {
        this.userId = userId;
        avatarImageView.setFitHeight(50);
        avatarImageView.setFitWidth(50);
        cellContainer.setSpacing(15);
    }

    public static String loadRating(int rating) {
        return switch (rating) {
            case 1 -> "★☆☆☆☆";
            case 2 -> "★★☆☆☆";
            case 3 -> "★★★☆☆";
            case 4 -> "★★★★☆";
            case 5 -> "★★★★★";
            default -> "☆☆☆☆☆";
        };
    }

    @Override
    protected void updateItem(Review review, boolean empty) {
        super.updateItem(review, empty);

        if (empty || review == null) {
            setGraphic(null);
        } else {
            setConfig(review.getUserId() == userId);
            avatarImageView.setImage(new Image(review.getAvatarPath()));
            reviewText.setText(review.getReview());
            ratingLabelOfOtherUser.setText(loadRating(review.getRating()));
            ratingLabelOfOtherUser.setTextFill(Color.GOLD);
            setGraphic(cellContainer);
        }
    }

    private void setConfig(boolean isCurrentClient) {
        if (isCurrentClient) {
            cellContainer.getChildren().setAll(contentBox, avatarImageView);
            cellContainer.setAlignment(Pos.CENTER_RIGHT);
        } else {
            cellContainer.getChildren().setAll(avatarImageView, contentBox);
        }
    }

}
