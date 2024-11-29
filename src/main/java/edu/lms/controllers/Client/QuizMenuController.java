package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizMenuController {

    @FXML
    private Label point;


    @FXML
    void btnGame(MouseEvent event) {
        SceneManager.switchScene(Constants.GAME_DASHBOARD_VIEW, true);
    }


    @FXML
    void btnExit(MouseEvent event) {
        SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW, true);
    }

    public void setPoint(int score) {
        point.setText("" + score * 100);
    }


}
