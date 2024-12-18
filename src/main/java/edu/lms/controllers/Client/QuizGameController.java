package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.LibraryManagementApplication;
import edu.lms.controllers.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizGameController implements Initializable {
    QuizGame gameData;


    @FXML
    private Button QuizNextBtn;

    @FXML
    private TextField SearchBar;

    @FXML
    private Button choiceA;

    @FXML
    private Button choiceB;

    @FXML
    private Button choiceC;

    @FXML
    private Button choiceD;

    @FXML
    private Label question;

    @FXML
    private HBox topChart;


    public void LoadGames(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryManagementApplication.class.getResource("/edu/lms/fxml/quiz-game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Games");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            gameData = new QuizGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        QuizNextBtn.setDisable(true);
        QuizNextBtn.setVisible(false);

        initGame();
        setQuestion();
    }

    private int questionCounter = 0;
    public static int score = 0;
    public static int wrong = 0;
    private static final int questionMax = 10;

    public void initGame() {
        gameData.setGame();
    }
    Question currentQuestion;



    public void setQuestion() {
        if (questionCounter > 10) {
            return;
        }
        Question q = gameData.getQuestion();
        currentQuestion = q;
        question.setText(q.getQuestion());
        question.setWrapText(true);
        String[] choices = q.getChoices();
        choiceA.setText(choices[0]);
        choiceB.setText(choices[1]);
        choiceC.setText(choices[2]);
        choiceD.setText(choices[3]);

        QuizNextBtn.setDisable(true);
        QuizNextBtn.setVisible(false);
        choiceA.setStyle("-fx-background-color: white;");
        choiceB.setStyle("-fx-background-color: white;");
        choiceC.setStyle("-fx-background-color: white;");
        choiceD.setStyle("-fx-background-color: white;");
        choiceA.setDisable(false);
        choiceB.setDisable(false);
        choiceC.setDisable(false);
        choiceD.setDisable(false);

        if (wrong == 3) {
            wrong = 0;
            QuizMenuController quizMenuController = SceneManager.switchScene(Constants.GAME_END,true);
            assert quizMenuController != null;
            quizMenuController.setPoint(score);
        }
    }

    @FXML
    public void handleChoiceA(ActionEvent event) {
        if (choiceA.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
            wrong++;
        }
        showRes();
    }

    @FXML
    public void handleChoiceB(ActionEvent event) {
        if (choiceB.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
            wrong++;
        }
        showRes();
    }

    @FXML
    public void handleChoiceC(ActionEvent event) {
        if (choiceC.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
            wrong++;
        }
        showRes();
    }

    @FXML
    public void handleChoiceD(ActionEvent event) {
        if (choiceD.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
            wrong++;
        }
        showRes();
    }

    @FXML
    public void handleNextQuestion(ActionEvent event) {
        // Chuyển sang câu hỏi tiếp theo
        setQuestion();
        questionCounter++;
    }

    public void showRes() {
        choiceA.setStyle("-fx-background-color: #de211b;");
        choiceB.setStyle("-fx-background-color: #de211b;");
        choiceC.setStyle("-fx-background-color: #de211b;");
        choiceD.setStyle("-fx-background-color: #de211b;");

        if(choiceA.getText().equals(currentQuestion.getAnswer())){
            choiceA.setStyle("-fx-background-color: #3bde1b;");
        }

        if(choiceB.getText().equals(currentQuestion.getAnswer())){
            choiceB.setStyle("-fx-background-color: #3bde1b;");
        }

        if(choiceC.getText().equals(currentQuestion.getAnswer())){
            choiceC.setStyle("-fx-background-color: #3bde1b;");
        }

        if(choiceD.getText().equals(currentQuestion.getAnswer())){
            choiceD.setStyle("-fx-background-color: #3bde1b;");
        }

        QuizNextBtn.setVisible(true);
        QuizNextBtn.setDisable(false);



        choiceA.setDisable(true);
        choiceB.setDisable(true);
        choiceC.setDisable(true);
        choiceD.setDisable(true);
    }


}