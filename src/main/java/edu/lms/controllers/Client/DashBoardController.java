package edu.lms.controllers.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DashBoardController implements Initializable {
    @FXML
    private HBox cardLayout;

    private List<edu.lms.models.book.Book> recentlyAdded;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recentlyAdded = new ArrayList<>(recentlyAdded());

        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<edu.lms.models.book.Book> recentlyAdded() {
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("RICH DAD\nPOOR DAD");
        book.setCoverImage("edu.lms.images.Books.rich_dad_poor_dad.jpg");
        book.setAuthor("Robert Kiyosaki");
        ls.add(book);





        return ls;
    }

}
