package edu.lms;

import edu.lms.controllers.SceneManager;
import edu.lms.models.book.BookManager;
import edu.lms.models.book.CategoriesManager;
import edu.lms.models.issue.IssuesManager;
import edu.lms.models.search.BookSearch;
import edu.lms.models.user.UserManager;
import edu.lms.services.EmailService;
import javafx.application.Application;
import javafx.concurrent.Task;
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
        SceneManager.setPrimaryStage(primaryStage);
        EmailService.initialize();
        loadData();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LibraryManagementApplication.class.getResource(Constants.SIGN_IN_VIEW));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("LMS");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void loadData() {
        Task<Void> initializationTask = new Task<>() {
            @Override
            protected Void call() {
                BookManager.initialize();
                UserManager.initialize();
                BookSearch.initialize();
                CategoriesManager.initialize();
                return null;
            }
        };

        initializationTask.setOnSucceeded(event -> {
            System.out.println("Initialization completed successfully!");
        });

        initializationTask.setOnFailed(event -> {
            Throwable exception = initializationTask.getException();
            System.err.println("Initialization failed: " + exception.getMessage());
        });

        new Thread(initializationTask).start();
    }

    public static void main(String[] args) {
        /*Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        System.out.println("width: " + screenBounds.getWidth());
        System.out.println("height: " + screenBounds.getHeight());*/
        launch(args);
    }
}

