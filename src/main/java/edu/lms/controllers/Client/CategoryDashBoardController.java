package edu.lms.controllers.Client;

import edu.lms.Constants;
import edu.lms.models.book.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @FXML
    private Button btnAll;

    @FXML
    private Button btnArt;

    @FXML
    private Button btnEducation;

    @FXML
    private Button btnHorror;

    @FXML
    private Button btnSelfhelp;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private List<edu.lms.models.book.Book> recentlyAdded;
    private List<edu.lms.models.book.Book> recommended;

    private boolean allShown = true;
    private boolean artShown = false;
    private boolean educationShown = false;
    private boolean horrorShown = false;
    private boolean selfhelpShown = false;

    // Reset tất cả các danh mục về false
    private void resetCategories() {
        allShown = artShown = educationShown = horrorShown = selfhelpShown = false;
    }

    // Xử lý khi nhấn các nút danh mục
    @FXML
    void pressCategory(ActionEvent event) {
        resetCategories(); // Reset trạng thái

        // Kiểm tra nút nào được nhấn và cập nhật trạng thái
        if (event.getSource() == btnAll) {
            allShown = true;
        } else if (event.getSource() == btnArt) {
            artShown = true;
        } else if (event.getSource() == btnEducation) {
            educationShown = true;
        } else if (event.getSource() == btnHorror) {
            horrorShown = true;
        } else if (event.getSource() == btnSelfhelp) {
            selfhelpShown = true;
        }

        // Load sách dựa trên trạng thái mới
        initializeBooks();
    }

    private void initializeBooks() {
        try {
            // Xóa nội dung hiện tại trong GridPane
            bookContainer.getChildren().clear();

            if (allShown) {
                displayBooks(Constants.recommended);
            } else if (artShown) {
                displayBooks(Constants.artBooks);
            } else if (educationShown) {
                displayBooks(Constants.educationBooks);
            } else if (horrorShown) {
                displayBooks(Constants.horrorBooks);
            } else if (selfhelpShown) {
                displayBooks(Constants.selfhelpBooks);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void displayBooks(List<Book> books) throws IOException {
        int column = 0;
        int row = 1;

        for (Book value : books) {
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Constants.recommended = constants.recommendedBook();
        Constants.horrorBooks = constants.getHorrorBooks();
        Constants.selfhelpBooks = constants.getSelfhelpBooks();
        Constants.educationBooks = constants.getEducationBooks();
        Constants.artBooks = constants.getArtBooks();

        initializeBooks();

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
