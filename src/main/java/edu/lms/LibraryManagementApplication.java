package edu.lms;

import edu.lms.controllers.SceneManager;
import edu.lms.services.EmailService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
            System.out.println('1');
            FXMLLoader loader = new FXMLLoader();
            System.out.println(2);
            loader.setLocation(LibraryManagementApplication.class.getResource(Constants.GAME_PLAY));
            System.out.println(3);
            Parent root = loader.load();
            System.out.println("4");
            Scene scene = new Scene(root);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX(screenBounds.getMinX());
            primaryStage.setY(screenBounds.getMinY());
            primaryStage.setWidth(screenBounds.getWidth());
            primaryStage.setHeight(screenBounds.getHeight());

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
        launch(args);
    }
}