package edu.lms.Controllers.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class CardController {


    @FXML
    private Label authorName;

    @FXML
    private HBox box;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    private String [] colors = {"B9E5FF", "BDB2FE", "FB9AAB", "FF5056"};

    public void setData(edu.lms.models.book.Book book) {
        Image image = new Image(getClass().getResourceAsStream(book.getCoverImage()));
        bookImage.setImage(image);

        bookName.setText(book.getTitle());
        authorName.setText(book.getAuthor());
        box.setStyle("-fx-background-color: " + Color.web(colors[(int)(Math.random()*colors.length)]));
    }
}
