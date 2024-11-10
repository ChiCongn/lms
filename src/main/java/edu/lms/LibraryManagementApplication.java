package edu.lms;

import edu.lms.controllers.SceneManager;
import edu.lms.services.Config;
import edu.lms.services.EmailService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

            loader.setLocation(LibraryManagementApplication.class.getResource(Constants.LIBRARIAN_DASHBOARD_VIEW));
            Parent root = loader.load();
            Scene scene = new Scene(root);

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