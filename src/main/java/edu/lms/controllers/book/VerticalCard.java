package edu.lms.controllers.book;


import edu.lms.models.book.Book;
import edu.lms.models.review.ReviewCell;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

public class VerticalCard {
    @FXML
    private Label authors;

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
    }

}
