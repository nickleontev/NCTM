package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.datasets.Assignee;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;

public class CreateAssigneeController {
    private static final Logger log = LoggerFactory.getLogger(CreateProjectController.class);
    private Root data = RuntimeDataHolder.getHolder();

    @FXML
    private Button create_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField fullName_TextField;

    @FXML
    private TextField phone_TextField;

    @FXML
    private TextField email_TextField;


    @FXML
    private Label parent_Label;


    private ResourceBundle resourceBundle;

    public void createAssignee(ActionEvent actionEvent) throws IOException {
        if (checkEmpty()) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            dialog.initOwner(stage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("This is a Dialog"));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
            return;
        }
        Assignee assignee = new Assignee(fullName_TextField.getText(), phone_TextField.getText(), email_TextField.getText());
       // data.setCurrentAssignee(assignee);
       // data.update();
        actionClose(actionEvent);

    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public  void update(){
        fullName_TextField.clear();
        phone_TextField.clear();
        email_TextField.clear();
    }
    private boolean checkEmpty(){
        return (fullName_TextField.getText().isEmpty() || phone_TextField.getText().isEmpty() || email_TextField.getText().isEmpty());
    }
}
