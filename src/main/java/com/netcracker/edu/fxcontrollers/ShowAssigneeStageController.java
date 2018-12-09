package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.datasets.Assignee;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ShowAssigneeStageController {
    private Root data = RuntimeDataHolder.getHolder();
    //private List<Assignee> assignees;

    @FXML
    public TableView assignees_TableView;


    @FXML
    public TableColumn fullName = new TableColumn("Full name");


    @FXML
    public TableColumn phone = new TableColumn("Phone number");


    @FXML
    public TableColumn email = new TableColumn("Email");



    public void initializeData() {
        ObservableList<Assignee> assignees = data.getObservableListAssignees();

        TableView<Assignee> tv = new TableView<>(assignees);
        

        fullName.setCellValueFactory( new PropertyValueFactory<Assignee, String>("fullName"));
        phone.setCellValueFactory( new PropertyValueFactory<Assignee, String>("phone"));
        email.setCellValueFactory( new PropertyValueFactory<Assignee, String>("email"));

        assignees_TableView.setItems(assignees);

        assignees_TableView.getColumns().addAll(fullName, phone, email);
       // assignees = data.getObservableListAssignees();
        //for(int i = 0; i < assignees.size(); i++){
//            assignees_TableView.setItems(data.getObservableListAssignees());
        //}
    }
}
