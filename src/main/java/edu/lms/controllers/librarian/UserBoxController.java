package edu.lms.controllers.dashboard;

import edu.lms.models.user.Client;
import edu.lms.models.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserBoxController {

    @FXML
    private Label userName;

    @FXML
    private ImageView userImage;


    public void setData(Client user) {
//        Image image = new Image(getClass().getResourceAsStream(book.getCoverImage()));
//        bookImage.setImage(image);
        userName.setText(user.getUsername());
    }
}
