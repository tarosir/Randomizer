package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.DataBase.DataBaseHandler;
import sample.Main;
import sample.Student;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RandomStudentVsStudentController {
    Main main = new Main();
    Stage stage = new Stage();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Label errorTEXT;

    @FXML
    private CheckBox bonusBallCheckAnswer;

    @FXML
    private CheckBox goodAnswerCheck;

    @FXML
    private CheckBox goodQuestionCheck;

    @FXML
    private Button nextRandom;

    @FXML
    private Button startRandom;

    @FXML
    private Label studentNameAnswer;

    @FXML
    private Label studentNameQuestion;

    List<Student> listStudent = new ArrayList<>();
    List<Student> listStudentQuestion = new ArrayList<>();
    List<Student> listStudentAnswer = new ArrayList<>();
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
            studentQuestion = listStudentQuestion.get((int) (Math.random() * listStudentQuestion.size()));
            studentAnswer = listStudentAnswer.get((int) (Math.random() * listStudentAnswer.size()));
            firstPair();
        });

        nextRandom.setOnAction(event -> {
            if (goodQuestionCheck.isSelected()) {
                studentQuestion.setQuestion("1");
                goodQuestionCheck.fire();
            } else studentQuestion.setQuestion("0");

            if (goodAnswerCheck.isSelected()) {
                studentAnswer.setAnswer("1");
                goodAnswerCheck.fire();
            } else studentAnswer.setAnswer("0");

            if (bonusBallCheckAnswer.isSelected()) {
                studentAnswer.setBonusBall("1");
                bonusBallCheckAnswer.fire();
            }

            for (Student student :
                    listStudent) {
                if (student.getId() == studentAnswer.getId()) {
                    student = studentAnswer;
                }
                if (student.getId() == studentQuestion.getId()) {
                    student = studentQuestion;
                }
            }
            updateLists();
            nextPair();
            studentNameAnswer.setText(studentAnswer.getLastname() + " " + studentAnswer.getName());
            studentNameQuestion.setText(studentQuestion.getLastname() + " " + studentQuestion.getName());
        });
    }

    private void updateLists() {
        listStudentQuestion = listStudent.stream().filter(a -> a.getQuestion().equals("x")).collect(Collectors.toList());
        listStudentAnswer = listStudent.stream().filter(a -> a.getAnswer().equals("x")).collect(Collectors.toList());
    }

    private void nextPair() {
        updateLists();
        if (listStudentAnswer.isEmpty() || listStudentQuestion.isEmpty()) {
            errorTEXT.setText("There is no more students left.");
        } else {
            if (studentAnswer.getQuestion().equals("x")) {
                studentQuestion = studentAnswer;
                studentAnswer = listStudentAnswer.stream()
                        .filter(a -> !a.equals(studentQuestion) && (a.getAnswer().equals("x")))
                        .collect(Collectors.toList())
                        .get((int) (Math.random() * listStudentAnswer.stream()
                                .filter(a -> !a.equals(studentQuestion) && (a.getAnswer().equals("x")))
                                .collect(Collectors.toList())
                                .size()));
                if (!studentAnswer.getTeam().equals(studentQuestion.getTeam())) {
                    studentNameAnswer.setText(studentAnswer.getLastname() + " " + studentAnswer.getName());
                } else nextPair();
            }
        }
    }

    private void firstPair() {
        updateLists();
        if (listStudentAnswer.isEmpty() || listStudentQuestion.isEmpty()) {
            errorTEXT.setText("There is no more students left.");
        } else {
            studentNameQuestion.setText(studentQuestion.getLastname() + " " + studentQuestion.getName());
            studentAnswer = listStudentAnswer.stream()
                    .filter(a -> !a.equals(studentQuestion) && (a.getAnswer().equals("x")))
                    .collect(Collectors.toList())
                    .get((int) (Math.random() * listStudentAnswer.stream()
                            .filter(a -> !a.equals(studentQuestion) && (a.getAnswer().equals("x")))
                            .collect(Collectors.toList())
                            .size()));
            if (studentAnswer.getQuestion().equals("x") && (!studentAnswer.getTeam().equals(studentQuestion.getTeam()))) {
                studentNameAnswer.setText(studentAnswer.getLastname() + " " + studentAnswer.getName());
            } else firstPair();
        }
    }
}
