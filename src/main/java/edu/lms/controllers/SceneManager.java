package edu.lms.controllers;

import edu.lms.LibraryManagementApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static <Controller> Controller switchScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource(fxmlFileName));
            AnchorPane root = loader.load();

            Scene newScene = new Scene(root);
            primaryStage.setScene(newScene);
            primaryStage.show();

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

