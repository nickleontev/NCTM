package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.dao.impl.runtime.Root;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.net.URL;
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



    private Root projects;

    private ResourceBundle resourceBundle;



    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void createProject(ActionEvent actionEvent) {
        if (!checkValues()){
            return;
        }

        Project project = new Project(summary_TextField.getText(), description_TextField.getText(), deadline_DatePicker.getValue());

        data.getCurrent().add(project);

        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (true){
            //DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("fill_field"));
            return true;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }

}
