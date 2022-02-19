package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DataBase.DataBaseHandler;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentController {
    Main main = new Main();
    Stage stage = new Stage();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addStudentButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField enterNameLastnameField;

    @FXML
    private Label errorText;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            try {
                main.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        addStudentButton.setOnAction(event -> {
            String[] addText = enterNameLastnameField.getText().trim().split("[^a-zA-Zа-яА-Я0-9_]+");
            if (!addText.equals("")) {
                DataBaseHandler.addStudentFromDB(addText[0], addText[1], addText[2]);
                errorText.setText(addText[0] + " " + addText[1] + " from team № " + addText[2] + "successfully added!");
                enterNameLastnameField.setText("");
            } else {
                errorText.setText("Wrong data inserted!");
            }
        });
    }
}
