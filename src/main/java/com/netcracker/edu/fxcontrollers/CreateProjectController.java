package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.datasets.Assignee;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.util.JsonUtil;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateProjectController implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(CreateProjectController.class);
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
    private ComboBox<Assignee> assignee_combo;


    private ResourceBundle resourceBundle;
    private List<Assignee> assignees;



    private void closeWindow(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void actionClose(ActionEvent actionEvent) {
        if (!summary_TextField.getText().equals("")||description_TextField.getText().length()>10) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("NCTM - Несохраненные данные");
            alert.setHeaderText("Имеются несохраненные данные. Вы уверены, что хотите отменить создание проекта?");
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


    public void createProject(ActionEvent actionEvent) {
        if (checkValues()){
            Project project = new Project(summary_TextField.getText(), description_TextField.getText(), deadline_DatePicker.getValue(),data.getCurrent());
            data.getCurrent().addSubProject(project);
           // JsonUtil.save(data, RuntimeDataHolder.PATH);
            closeWindow(actionEvent);
        }
    }

    private boolean checkValues() {
        if (summary_TextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("NCTM - Cоздание проекта");
            alert.setHeaderText("Необходимо указать название проекта!");
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

        //assignees = data.getObservableListAssignees();
        //assignee_combo.setItems(data.getObservableListAssignees());
        //assignee_combo.setItems(data.getObservableListAssignees());
    }

    public  void update(){
        parent_Label.setText(data.getCurrent().getSummary());
        description_TextField.clear();
        summary_TextField.clear();
    }

}
