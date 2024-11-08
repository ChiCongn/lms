package edu.lms.controllers.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DashBoardController implements Initializable {
    @FXML
    private HBox cardLayout;

    @FXML
    private GridPane bookContainer;


    private List<edu.lms.models.book.Book> recentlyAdded;
    private List<edu.lms.models.book.Book> recommended;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        recommended = new ArrayList<>(books());
        int column = 0;
        int row = 1;
        try {
            for (edu.lms.models.book.Book value : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/edu/lms/fxml/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                cardLayout.getChildren().add(cardBox);
            }

            for(edu.lms.models.book.Book value : recommended) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/edu/lms/fxml/book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(value);

                if (column == 6) {
                    column = 0;
                    ++row;
                }

                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<edu.lms.models.book.Book> recentlyAdded() {
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("RICH DAD\nPOOR DAD");
        book.setCoverImage("/edu/lms/images/Books/rich_dad_poor_dad.jpg");
        book.setAuthor("Robert Kiyosaki");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("MAN'S SEARCHING\n FOR MEANING");
        book1.setCoverImage("/edu/lms/images/Books/man's searching for meaning.jpg");
        book1.setAuthor("Viktor Frankl");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("THE STOICISM'S\n MIND");
        book2.setCoverImage("/edu/lms/images/Books/stoicism.jpg");
        book2.setAuthor("Nancy Sherman");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("THE STORYTELLER'S\n SECRET");
        book3.setCoverImage("/edu/lms/images/Books/storyteller's secrete.jpg");
        book3.setAuthor("Carmine Gallo");
        ls.add(book3);

        return ls;
    }

    private List<edu.lms.models.book.Book> books(){
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("HARRY POTTER");
        book.setCoverImage("/edu/lms/images/Books/HarryPotter.jpg");
        book.setAuthor("J.K.Rowling");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("HOME");
        book1.setCoverImage("/edu/lms/images/Books/Home.jpg");
        book1.setAuthor("Lisa Allen");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("The Dark Side\n of The Mirror");
        book2.setCoverImage("/edu/lms/images/Books/TheDarkSideOfMirror.jpg");
        book2.setAuthor("Christopher Murphy");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("The UnderStory");
        book3.setCoverImage("/edu/lms/images/Books/TheUnderStory.jpg");
        book3.setAuthor("Saner Sangsuk");
        ls.add(book3);

        edu.lms.models.book.Book book4 = new edu.lms.models.book.Book();
        book4.setTitle("Educated");
        book4.setCoverImage("/edu/lms/images/Books/Educated.jpg");
        book4.setAuthor("Tara Westover");
        ls.add(book4);

        edu.lms.models.book.Book book5 = new edu.lms.models.book.Book();
        book5.setTitle("A million to one");
        book5.setCoverImage("/edu/lms/images/Books/a million to one.jpg");
        book5.setAuthor("Tony Faggioli");
        ls.add(book5);

        edu.lms.models.book.Book book6 = new edu.lms.models.book.Book();
        book6.setTitle("To Kill a Mockingbird");
        book6.setCoverImage("/edu/lms/images/Books/toKillaMockBird.jpg");
        book6.setAuthor("Harper Lee");
        ls.add(book6);

        edu.lms.models.book.Book book7 = new edu.lms.models.book.Book();
        book7.setTitle("Frankenstein");
        book7.setCoverImage("/edu/lms/images/Books/Frankenstein.jpg");
        book7.setAuthor("Marry Shelley");
        ls.add(book7);

        edu.lms.models.book.Book book8 = new edu.lms.models.book.Book();
        book8.setTitle("Beloved");
        book8.setCoverImage("/edu/lms/images/Books/Beloved.jpg");
        book8.setAuthor("Toni Morrison");
        ls.add(book8);


        return ls;
    }

}
