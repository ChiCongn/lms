package edu.lms.controllers.Client;

import edu.lms.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizMenuController {
    Constants constants = new Constants();
    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private static Label Point;


    @FXML
    void btnGame(MouseEvent event) {
        constants.switchScene(Constants.GAME_DASHBOARD_VIEW, event, stage, scene, root);
    }


    @FXML
    void btnExit(MouseEvent event) {
        constants.switchScene(Constants.TRENDING_DASHBOARD_VIEW, event, stage, scene, root);
    }

    public static void setPoint(int score) {
        Point.setText("" + score*100);
    }


}
