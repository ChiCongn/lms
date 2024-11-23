package edu.lms.controllers.Client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
    String question;
    String[] choices;
    String answer;

    public Question() {

    }

    public Question(String question, String[] choices, String answer) {
        this.question = question;
        this.choices = choices;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void shuffleChoices() {

        List<String> choiceList = Arrays.asList(choices);
        Collections.shuffle(choiceList);

        choices = choiceList.toArray(new String[0]);

        int correctIndex = choiceList.indexOf(answer);
        if (correctIndex != -1) {
            answer = choices[correctIndex];
        }
    }
}