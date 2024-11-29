package edu.lms.controllers;

import edu.lms.LibraryManagementApplication;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage) {
        SceneManager.primaryStage = primaryStage;
    }


    public static <Controller> Controller switchScene(String fxmlFileName, boolean isFullScreen) {
        try {
            FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource(fxmlFileName));
            Parent root = loader.load();

            if (isFullScreen) {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX(screenBounds.getMinX());
                primaryStage.setY(screenBounds.getMinY());
                primaryStage.setWidth(screenBounds.getWidth());
                primaryStage.setHeight(screenBounds.getHeight());
            }

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

