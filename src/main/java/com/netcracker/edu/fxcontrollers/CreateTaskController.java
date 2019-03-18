package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.fxmodel.Assignee;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Task;
import com.netcracker.edu.util.JsonUtil;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(CreateTaskController.class);

    private Root data = RuntimeDataHolder.getHolder();

    @FXML
    private Button create_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField summary_TextField;

    @FXML
    private TextArea description_TextField;

    @FXML
    private DatePicker deadline_DatePicker;

    @FXML
    private Label parent_Label;

    @FXML
    public ComboBox assignee_ComboBox;


    private ResourceBundle resourceBundle;

    private void closeWindow(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void actionClose(ActionEvent actionEvent) {
        if (!summary_TextField.getText().equals("")||description_TextField.getText().length()>10) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("NCTM - Несохраненные данные");
            alert.setHeaderText("Имеются несохраненные данные. Вы уверены, что хотите отменить добавление задачи?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    closeWindow(actionEvent);
                }
                if (rs == ButtonType.CANCEL) {
                    return;
                }
            });
        }
        else closeWindow(actionEvent);
    }


    public void createTask(ActionEvent actionEvent) {
        if (checkValues()){
            Task task = new Task(summary_TextField.getText(), description_TextField.getText(), deadline_DatePicker.getValue(),data.getCurrent(), (Assignee) assignee_ComboBox.getSelectionModel().getSelectedItem());
            data.getCurrent().addTask(task);
         //   JsonUtil.save(data, RuntimeDataHolder.PATH);
            closeWindow(actionEvent);
        }
    }

    private boolean checkValues() {
        if (summary_TextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("NCTM - Добаваление задачи");
            alert.setHeaderText("Необходимо указать название задачи!");
            alert.showAndWait().ifPresent(rs -> {
            });
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resourceBundle = resources;
        parent_Label.setText(data.getCurrent().getSummary());
        assignee_ComboBox.setItems(data.getAssignees());
        assignee_ComboBox.getSelectionModel().select(0);
    }

    public  void update(){
        parent_Label.setText(data.getCurrent().getSummary());
        description_TextField.clear();
        summary_TextField.clear();

    }

}
