package edu.lms.controllers.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class CardDetailsController {


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
        authorName.setText(book.getAuthors());
        box.setStyle(
                "-fx-background-radius: 15;"
                + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }

}
