package edu.lms;

import edu.lms.controllers.SceneManager;
import edu.lms.services.EmailService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;

public class LibraryManagementApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        EmailService.initialize();

        try {
            SceneManager sceneManager = new SceneManager(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LibraryManagementApplication.class.getResource(Constants.SIGN_IN_VIEW));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            /*Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX(screenBounds.getMinX());
            primaryStage.setY(screenBounds.getMinY());
            primaryStage.setWidth(screenBounds.getWidth());
            primaryStage.setHeight(screenBounds.getHeight());*/

            primaryStage.setScene(scene);
            primaryStage.setTitle("LMS");
            //primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        /*Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        System.out.println("width = " + screenWidth);
        System.out.println("Height = " + screenHeight);*/
        launch(args);
    }
}