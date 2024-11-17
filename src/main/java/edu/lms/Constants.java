package edu.lms;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Constants {
    public static final String SIGN_IN_VIEW = "/edu/lms/fxml/sign-in-view.fxml";
    public static final String SIGNUP_VIEW = "/edu/lms/fxml/sign-up-view.fxml";

    public static final String ADMIN_DASHBOARD_VIEW = "/edu/lms/fxml/dashboard-view.fxml";
    public static final String LIBRARIAN_DASHBOARD_VIEW = "/edu/lms/fxml/librarian-dashboard-view.fxml";
    public static final String CLIENT_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String TRENDING_DASHBOARD_VIEW = "/edu/lms/fxml/trending-dashboard.fxml";
    public static final String DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String CATEGORY_DASHBOARD_VIEW = "/edu/lms/fxml/category-dashboard.fxml";
    public static final String READING_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String FAVOURITE_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String HISTORY_DASHBOARD_VIEW = "/edu/lms/fxml/history-dashboard.fxml";
    public static final String GAME_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";


    public static final String TRENDING_DASHBOARD_VIEW = "/edu/lms/fxml/trending-dashboard.fxml";
    public static final String DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String CATEGORY_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String READING_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String FAVOURITE_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String HISTORY_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String GAME_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";

    public static final String CLIENT_MANAGEMENT_VIEW = "/edu/lms/fxml/clients-management-view.fxml";
    public static final String BOOKS_MANAGEMENT_VIEW = "/edu/lms/fxml/books-management-view.fxml";
    public static final String CLIENTS_DETAILS_VIEW = "/edu/lms/fxml/user-details-view.fxml";
    public static final String BOOK_DETAILS_VIEW = "/edu/lms/fxml/book-details-view.fxml";



    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchScene(String fxmlPath, MouseEvent event, Stage stage, Scene scene, Parent root ) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
