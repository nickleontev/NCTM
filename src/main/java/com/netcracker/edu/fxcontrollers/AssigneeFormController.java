package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.fxmodel.Assignee;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Task;
import com.netcracker.edu.fxmodel.TaskStatus;
import com.netcracker.edu.util.JsonUtil;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;

public class AssigneeFormController implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(AssigneeFormController.class);
    private Root data = RuntimeDataHolder.getHolder();
    private ResourceBundle resourceBundle;
    private Assignee current;

    @FXML
    private TextField name_TextField;
    @FXML
    private TableView <Assignee> assignees_TableView;
    @FXML
    private TableColumn <Assignee, String> assignee_TableColumn;

    ContextMenu tableViewContextMenu;

    private void closeWindow(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resourceBundle = resources;
        assignees_TableView.setEditable(true);
        tableViewContextMenuSetting();
        assignees_TableView.setContextMenu(tableViewContextMenu);

        assignee_TableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        assignee_TableColumn.setCellValueFactory(new PropertyValueFactory<Assignee, String>("name"));

        assignee_TableColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Assignee, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Assignee, String> t) {

                        Assignee assignee= (Assignee) t.getTableView().getItems().get(t.getTablePosition().getRow());

                        if (!assignee.equals(data.getRootAssignee())) {
                            assignee.setName(t.getNewValue());
                            return;
                        }
                        assignees_TableView.setItems(data.getAssignees());
                        return;
                    }
                }
        );


        assignees_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               current = newSelection;
            }
            else {

            }
        });

        assignees_TableView.setItems(data.getAssignees());
    }

    public  void update(){

    }


    private boolean checkValues() {
        if (name_TextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("NCTM - Добаваление исполнителя");
            alert.setHeaderText("Необходимо указать имя исполнителя!");
            alert.showAndWait().ifPresent(rs -> {
            });
            return false;
        }

        return true;
    }

    public void add_Click(ActionEvent actionEvent) {
        if (checkValues()){
            data.getAssignees().add(new Assignee(name_TextField.getText()));
            name_TextField.clear();
            //JsonUtil.save(data, RuntimeDataHolder.PATH);
        }
    }

    private void tableViewContextMenuSetting() {

        tableViewContextMenu = new ContextMenu();

        MenuItem item = new MenuItem("Удалить");
        item.setOnAction(event -> {
            deleteTaskDialog(current);
        });
        // Add MenuItem to ContextMenu
        tableViewContextMenu.getItems().addAll(item);
    }

    private void deleteTaskDialog(Assignee assignee) {

        if (assignee == data.getRootAssignee()) {
            return;
        }

        if (assignee!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("NTCM - Удаление исполнителя");
            alert.setHeaderText(new Formatter().format("Вы уверены, что хотите удалить исполнителя '%s'?", assignee).toString());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    data.removeAssignee(assignee);
                   // JsonUtil.save(data, RuntimeDataHolder.PATH);
                }
                if (rs == ButtonType.CANCEL) {
                    return;
                }
            });
        }
    }
}
