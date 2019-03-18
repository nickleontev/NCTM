package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.datasets.Assignee;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.util.JsonUtil;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditProjectController implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(EditProjectController.class);
    private Root data = RuntimeDataHolder.getHolder();

    @FXML
    private Button ok_btn;

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


    public void ok_Click(ActionEvent actionEvent) {
        if (checkValues()){
            data.getCurrent().setSummary(summary_TextField.getText());
            data.getCurrent().setDescription(description_TextField.getText());
            data.getCurrent().setDeadline(deadline_DatePicker.getValue());
         //   JsonUtil.save(data, RuntimeDataHolder.PATH);
            closeWindow(actionEvent);
        }
    }

    private boolean checkValues() {
        if (summary_TextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("NCTM - Проект");
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
      //  parent_Label.setText(data.getCurrent().getParentProject().getSummary());
        summary_TextField.setText(data.getCurrent().getSummary());
        description_TextField.setText(data.getCurrent().getDescription());
        deadline_DatePicker.setValue(data.getCurrent().getDeadline());

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
