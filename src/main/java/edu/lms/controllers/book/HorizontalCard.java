package edu.lms.controllers.book;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.client.ClientBookDetailsController;
import edu.lms.models.book.Book;
import edu.lms.models.review.ReviewCell;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class HorizontalCard {
    private static String[] colors = {"B9E5FF", "BDB2FE", "FB9AAB", "FF5056"};

    @FXML
    private Label authors;

    @FXML
    private HBox horizontalCard;

    @FXML
    private Label rating;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Label title;

    private Book book;


    public void setData(Book book) {
        this.book = book;
        thumbnail.setImage(new Image(book.getCoverImage()));
        title.setText(book.getTitle());
        authors.setText(book.getAuthors());
        rating.setText(ReviewCell.loadRating(book.getRating().intValue()));
        horizontalCard.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)] + ";"
                + "-fx-background-radius: 15;"
                + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }

}
