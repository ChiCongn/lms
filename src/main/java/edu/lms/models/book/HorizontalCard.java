package edu.lms.models.book;

import edu.lms.models.review.ReviewCell;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class HorizontalCard extends HBox {
    ImageView thumbnail;
    VBox content;
    Label title;
    Label author;
    Label rating;

    public HorizontalCard(Book book) {
        thumbnail = new ImageView(new Image(book.getCoverImage()));
        thumbnail.setFitWidth(115);
        thumbnail.setFitHeight(165);
        title = new Label(book.getTitle());
        title.prefWidth(180);
        title.setWrapText(true);
        title.setTextAlignment(TextAlignment.CENTER);
        author = new Label(book.getAuthors());
        author.prefWidth(150);
        author.setWrapText(true);
        author.setTextAlignment(TextAlignment.CENTER);
        rating = new Label(ReviewCell.loadRating(book.getRating().intValue()));
        rating.setTooltip(new Tooltip(book.getRating().toString()));
        rating.setTextFill(Color.GOLD);
        content = new VBox(5, title, author, rating);
        this.getChildren().addAll(thumbnail, content);
        this.setStyle("-fx-alignment: center; -fx-border-color: lightgray; -fx-padding: 10;");
        this.setPrefWidth(500);
    }
}
