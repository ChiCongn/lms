package edu.lms;

import edu.lms.controllers.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LibraryManagementApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            SceneManager sceneManager = new SceneManager(primaryStage);

            URL fxmlLocation = getClass().getResource("/edu/lms/fxml/TestingDashBoard.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found at specified path!");
            }
            else {
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setLocation(LibraryManagementApplication.class.getResource(Constants.WELCOME_VIEW));
                AnchorPane root = loader.load();
                Scene scene = new Scene(root, 600, 400);
                primaryStage.setScene(scene);
                primaryStage.setTitle("LMS");
                primaryStage.setResizable(false);
                primaryStage.show();
            }




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
