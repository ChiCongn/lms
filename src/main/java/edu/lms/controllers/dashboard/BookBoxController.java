package edu.lms.controllers.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookBoxController {

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;



    public void setData(edu.lms.models.book.Book book) {
        Image image = new Image(getClass().getResourceAsStream(book.getCoverImage()));
        bookImage.setImage(image);
        bookName.setText(book.getTitle());
    }
}
