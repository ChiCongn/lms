package edu.lms.controllers.Client;

import edu.lms.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrendingDashboardController implements Initializable {
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

    Constants constants = new Constants();

    private Stage stage;
    private Scene scene;
    private Parent root;

    //private List<edu.lms.models.book.Book> recentlyAdded;
    //private List<edu.lms.models.book.Book> recommended;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //recommended = new ArrayList<>(books());
        Constants.recommended = constants.recommendedBook();
        int column = 0;
        int row = 1;

        try {

            for (edu.lms.models.book.Book value : Constants.recommended) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/edu/lms/fxml/card-details.fxml"));
                HBox bookBox = fxmlLoader.load();
                CardDetailsController bookController = fxmlLoader.getController();
                bookController.setData(value);


                if (column == 1) {
                    column = 0;
                    ++row;
                }

                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));

                // Sự kiện click vào bookBox
//                bookBox.setOnMouseClicked(event -> {
//                    try {
//                        FXMLLoader detailsLoader = new FXMLLoader(getClass().getResource("/edu/lms/fxml/book-details.fxml"));
//                        BorderPane bookDetails = detailsLoader.load();
//                        BookDetailsController detailsController = detailsLoader.getController();
//                        detailsController.setData(value); // Truyền dữ liệu sách vào BookDetailsController
//
//                        // Chuyển scene
//                        cardLayout.getScene().setRoot(bookDetails);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





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
