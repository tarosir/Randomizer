package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.DataBase.DataBaseHandler;

import java.io.IOException;


public class MainController {
    Stage stage = new Stage();



    @FXML
    private Button addStudentButton;

    @FXML
    private Button deleteStudentButton;

    @FXML
    private Button randomStudentVsStudentButton;

    @FXML
    private Button setStudentButton;

    @FXML
    private Button showStudentButton;

    @FXML
    private Button clearMarksButton;

    @FXML
    private Label successText;

    @FXML
    void initialize() {
        clickButton(addStudentButton, "/fxml/AddStudentMenu.fxml");

        clickButton(deleteStudentButton, "/fxml/DeleteStudent.fxml");

        clickButton(setStudentButton, "/fxml/SetStudent.fxml");

        clickButton(showStudentButton, "/fxml/ShowStudents.fxml");

        clickButton(randomStudentVsStudentButton, "/fxml/RandomStudentVsStudent.fxml");

        clearMarksButton.setOnAction((event) -> {
            DataBaseHandler.clearMarks();
            successText.setText(" Marks successfully cleared!");
        });
    }

    private void clickButton(Button addStudentButton, String s) {
        addStudentButton.setOnAction(event -> {
            addStudentButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(s));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}
