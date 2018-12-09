package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Task;
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


    private ResourceBundle resourceBundle;



    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();


    }


    public void createTask(ActionEvent actionEvent) {
        if (!checkValues()){
            return;
        }

        Task task = new Task(summary_TextField.getText(), description_TextField.getText(), deadline_DatePicker.getValue(),data.getCurrent());

        data.getCurrent().addTask(task);


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
        parent_Label.setText(data.getCurrent().getSummary());

    }

    public  void update(){
        parent_Label.setText(data.getCurrent().getSummary());
        description_TextField.clear();
        summary_TextField.clear();

    }

}
