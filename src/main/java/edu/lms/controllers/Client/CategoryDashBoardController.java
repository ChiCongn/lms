package edu.lms.controllers.Client;

import edu.lms.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryDashBoardController implements Initializable {
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane bookContainer;

    @FXML
    private HBox Browse;

    @FXML
    private HBox Categories;

    @FXML
    private HBox Favorite;

    @FXML
    private HBox History;

    @FXML
    private HBox Reading;

    @FXML
    private TextField SearchBar;

    @FXML
    private HBox topChart;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private List<edu.lms.models.book.Book> recentlyAdded;
    private List<edu.lms.models.book.Book> recommended;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        recommended = new ArrayList<>(books());
        int column = 0;
        int row = 1;

        try {
            for (edu.lms.models.book.Book value : recommended) {
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

                // Sự kiện click vào bookBox
                bookBox.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader detailsLoader = new FXMLLoader(getClass().getResource("/edu/lms/fxml/book-details.fxml"));
                        BorderPane bookDetails = detailsLoader.load();
                        BookDetailsController detailsController = detailsLoader.getController();
                        detailsController.setData(value); // Truyền dữ liệu sách vào BookDetailsController

                        // Chuyển scene
                        cardLayout.getScene().setRoot(bookDetails);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
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
        book.setAuthors("Robert Kiyosaki");
        book.setDescription("abcs");
        book.setPublishedYear("111");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("MAN'S SEARCHING\n FOR MEANING");
        book1.setCoverImage("/edu/lms/images/Books/man's searching for meaning.jpg");
        book1.setAuthors("Viktor Frankl");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("THE STOICISM'S\n MIND");
        book2.setCoverImage("/edu/lms/images/Books/stoicism.jpg");
        book2.setAuthors("Nancy Sherman");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("THE STORYTELLER'S\n SECRET");
        book3.setCoverImage("/edu/lms/images/Books/storyteller's secrete.jpg");
        book3.setAuthors("Carmine Gallo");
        ls.add(book3);

        return ls;
    }

    private List<edu.lms.models.book.Book> books(){
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("HARRY POTTER");
        book.setCoverImage("/edu/lms/images/Books/HarryPotter.jpg");
        book.setAuthors("J.K.Rowling");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("HOME");
        book1.setCoverImage("/edu/lms/images/Books/Home.jpg");
        book1.setAuthors("Lisa Allen");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("The Dark Side\n of The Mirror");
        book2.setCoverImage("/edu/lms/images/Books/TheDarkSideOfMirror.jpg");
        book2.setAuthors("Christopher Murphy");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("The UnderStory");
        book3.setCoverImage("/edu/lms/images/Books/TheUnderStory.jpg");
        book3.setAuthors("Saner Sangsuk");
        ls.add(book3);

        edu.lms.models.book.Book book4 = new edu.lms.models.book.Book();
        book4.setTitle("Educated");
        book4.setCoverImage("/edu/lms/images/Books/Educated.jpg");
        book4.setAuthors("Tara Westover");
        ls.add(book4);

        edu.lms.models.book.Book book5 = new edu.lms.models.book.Book();
        book5.setTitle("A million to one");
        book5.setCoverImage("/edu/lms/images/Books/a million to one.jpg");
        book5.setAuthors("Tony Faggioli");
        ls.add(book5);

        edu.lms.models.book.Book book6 = new edu.lms.models.book.Book();
        book6.setTitle("To Kill a Mockingbird");
        book6.setCoverImage("/edu/lms/images/Books/toKillaMockBird.jpg");
        book6.setAuthors("Harper Lee");
        ls.add(book6);

        edu.lms.models.book.Book book7 = new edu.lms.models.book.Book();
        book7.setTitle("Frankenstein");
        book7.setCoverImage("/edu/lms/images/Books/Frankenstein.jpg");
        book7.setAuthors("Marry Shelley");
        ls.add(book7);

        edu.lms.models.book.Book book8 = new edu.lms.models.book.Book();
        book8.setTitle("Beloved");
        book8.setCoverImage("/edu/lms/images/Books/Beloved.jpg");
        book8.setAuthors("Toni Morrison");
        ls.add(book8);


        return ls;
    }


    private void switchScene(String fxmlPath, MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    Constants constants = new Constants();

    @FXML
    public void switchToTopChart(MouseEvent event) throws IOException {
        constants.switchScene(Constants.TRENDING_DASHBOARD_VIEW, event, stage, scene, root);
    }

    @FXML
    public void switchToBrowse(MouseEvent event) throws IOException {
        constants.switchScene(Constants.DASHBOARD_VIEW, event, stage, scene, root);
    }

    @FXML
    public void switchToGenre(MouseEvent event) throws IOException {
        constants.switchScene(Constants.CATEGORY_DASHBOARD_VIEW, event, stage, scene, root);
    }

    @FXML
    public void switchToReading(MouseEvent event) throws IOException {
        constants.switchScene(Constants.READING_DASHBOARD_VIEW, event, stage, scene, root);
    }

    @FXML
    public void switchToFavourite(MouseEvent event) throws IOException {
        constants.switchScene(Constants.FAVOURITE_DASHBOARD_VIEW, event, stage, scene, root);
    }

    @FXML
    public void switchToHistory(MouseEvent event) throws IOException {
        constants.switchScene(Constants.HISTORY_DASHBOARD_VIEW, event, stage, scene, root);
    }

    @FXML
    public void switchToGame(MouseEvent event) throws IOException {
        constants.switchScene(Constants.GAME_DASHBOARD_VIEW, event, stage, scene, root);
    }



}
