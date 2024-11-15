package edu.lms.controllers.Client;

import edu.lms.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.lms.models.book.Book;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BookDetailsController {
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
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private Label description;

    @FXML
    private Label language;

    @FXML
    private Label publishedYear;

    @FXML
    private HBox topChart;

    @FXML
    private Label totalCopies;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setData(Book book) {
        Image image = new Image(getClass().getResourceAsStream(book.getCoverImage()));
        bookImage.setImage(image);
        bookName.setText(book.getTitle());
        authorName.setText(book.getAuthors());
        description.setText(book.getDescription());
        language.setText(book.getLanguage());

        // Chuyển đổi thành chuỗi trước khi hiển thị
        publishedYear.setText(String.valueOf(book.getPublishedYear()));
        totalCopies.setText(String.valueOf(book.getTotalCopies()));
    }

    private void switchScene(String fxmlPath, MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void switchToTopChart(MouseEvent event) throws IOException {
        switchScene(Constants.TRENDING_DASHBOARD_VIEW, event);
    }

    @FXML
    public void switchToBrowse(MouseEvent event) throws IOException {
        switchScene(Constants.DASHBOARD_VIEW, event);
    }

    @FXML
    public void switchToGenre(MouseEvent event) throws IOException {
        switchScene(Constants.CATEGORY_DASHBOARD_VIEW, event);
    }

    @FXML
    public void switchToReading(MouseEvent event) throws IOException {
        switchScene(Constants.READING_DASHBOARD_VIEW, event);
    }

    @FXML
    public void switchToFavourite(MouseEvent event) throws IOException {
        switchScene(Constants.FAVOURITE_DASHBOARD_VIEW, event);
    }

    @FXML
    public void switchToHistory(MouseEvent event) throws IOException {
        switchScene(Constants.HISTORY_DASHBOARD_VIEW, event);
    }

    @FXML
    public void switchToGame(MouseEvent event) throws IOException {
        switchScene(Constants.GAME_DASHBOARD_VIEW, event);
    }
}
