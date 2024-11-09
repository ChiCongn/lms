package edu.lms;

import edu.lms.controllers.SceneManager;
import edu.lms.services.EmailService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
            System.out.println('1');
            FXMLLoader loader = new FXMLLoader();
            System.out.println(2);
            loader.setLocation(LibraryManagementApplication.class.getResource(Constants.SIGN_IN_VIEW));
            System.out.println(3);
            AnchorPane root = loader.load();
            System.out.println("4");
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("LMS");
            primaryStage.setResizable(false);
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
