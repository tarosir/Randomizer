package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DataBase.DataBaseHandler;
import sample.Main;
import sample.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowStudentsController {
    Main main = new Main();
    Stage stage = new Stage();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Student, String> collumAnswer;

    @FXML
    private TableColumn<Student, String> collumBonus;

    @FXML
    private TableColumn<Student, String> collumLastName;

    @FXML
    private TableColumn<Student, String> collumTeamName;

    @FXML
    private TableColumn<Student, String> collumName;

    @FXML
    private TableColumn<Student, String> collumQuestion;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, Integer> collumId;

    ObservableList<Student> observableList = FXCollections.observableList(DataBaseHandler.getAllStudentsFromDB());

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            try {
                main.start(stage);
                observableList.clear();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        collumId.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        collumName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        collumLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastname"));
        collumTeamName.setCellValueFactory(new PropertyValueFactory<Student, String>("team"));
        collumQuestion.setCellValueFactory(new PropertyValueFactory<Student, String>("question"));
        collumAnswer.setCellValueFactory(new PropertyValueFactory<Student, String>("answer"));
        collumBonus.setCellValueFactory(new PropertyValueFactory<Student, String>("bonusBall"));

        table.setItems(observableList);
    }
}
