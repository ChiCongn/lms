package edu.lms.models.book;

import edu.lms.models.review.ReviewCell;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Card extends VBox {
    ImageView thumbnail;
    Label title;
    Label author;
    Label rating;

    public Card(Book book) {
        System.out.println("create card");
        //thumbnail = new ImageView(new Image("file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/images/placeholder.png"));
        thumbnail = new ImageView(new Image(book.getCoverImage()));
        thumbnail.setFitWidth(100);
        thumbnail.setFitHeight(150);
        title = new Label(book.getTitle());
        title.prefWidth(150);
        title.setWrapText(true);
        title.setTextAlignment(TextAlignment.CENTER);
        author = new Label(book.getAuthors());
        author.prefWidth(150);
        author.setWrapText(true);
        author.setTextAlignment(TextAlignment.CENTER);
        rating = new Label(ReviewCell.loadRating(book.getRating().intValue()));
        rating.setTooltip(new Tooltip(book.getRating().toString()));
        rating.setTextFill(Color.GOLD);
        this.getChildren().addAll(thumbnail, title, author, rating);
        this.setStyle("-fx-alignment: center; -fx-border-color: lightgray; -fx-padding: 10;");
    }

    public void loadThumbnail(String imageUrl) {
        thumbnail.setImage(new Image(imageUrl, true));
    }

}
