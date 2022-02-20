package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.DataBase.DataBaseHandler;
import sample.Main;
import sample.Student;


import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

public class RandomStudentVsStudentController {
    Main main = new Main();
    Stage stage = new Stage();


    @FXML
    private Button backButton;

    @FXML
    private Label errorTEXT;


    @FXML
    private Button startRandom;

    @FXML
    private Label studentNameAnswer;

    @FXML
    private Label studentNameQuestion;

    List<Student> listStudent = new ArrayList<>();
    List<Student> listStudentQuestion = new ArrayList<>();

    Student studentQuestion;
    Student studentAnswer;

    @FXML
    void initialize() {
        listStudent.addAll(DataBaseHandler.getAllStudentsFromDB());
        updateLists();
        backButton.setOnAction(event -> {
            for (Student student :
                    listStudent) {
                DataBaseHandler.setQuestionAndAnswerAndBalls(student);
            }
            backButton.getScene().getWindow().hide();
            try {
                main.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        startRandom.setOnAction(event -> {
            updateLists();
            randomize();


        });


    }

    private void updateLists() {
        listStudentQuestion = listStudent.stream().filter(a -> a.getQuestion().equals("x")).collect(Collectors.toList());

    }


    private void randomize() {
        updateLists();
        if (listStudentQuestion.size() < 2) {
            errorTEXT.setText("There is no more  pairs left.");
        } else {
            studentQuestion = listStudentQuestion.get((int) (Math.random() * listStudentQuestion.size()));
            studentQuestion.setQuestion("done");
            studentNameQuestion.setText(studentQuestion.getLastname() + " " + studentQuestion.getName()+ " " + studentAnswer.getTeam());
            updateLists();
            randomSecond();
        }
    }

    private void randomSecond() {

        studentAnswer = listStudentQuestion.get((int) (Math.random() * listStudentQuestion.size()));


        if ((!studentAnswer.getTeam().equals(studentQuestion.getTeam()))) {
            studentAnswer.setQuestion("done");
            studentNameAnswer.setText(studentAnswer.getLastname() + " " + studentAnswer.getName()+ " " + studentAnswer.getTeam());
            updateLists();
        } else randomSecond();
    }

    private void randomAll() {
        int i = listStudent.size() / 2;
        while (i != 0) {
            randomize();
            i--;
        }
    }


}
