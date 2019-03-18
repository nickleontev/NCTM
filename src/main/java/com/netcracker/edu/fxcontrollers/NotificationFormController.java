package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.fxmodel.*;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.ResourceBundle;

public class NotificationFormController implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(NotificationFormController.class);
    private Root data = RuntimeDataHolder.getHolder();
    private ResourceBundle resourceBundle;
    private Notification current;

    @FXML
    private TextField name_TextField;
    @FXML
    private TableView <Notification> notification_TableView;
    @FXML
    private TableColumn <Notification, LocalDate> date_TableColumn;

    @FXML
    private TableColumn <Notification, LocalTime> time_TableColumn;

    @FXML
    private TableColumn <Notification, NotificationMode> mode_TableColumn;

    @FXML
    private ComboBox<NotificationMode> mode_ComboBox;

    @FXML
    private ComboBox <Integer> hours_ComboBox;

    @FXML
    private ComboBox <Integer> minutes_ComboBox;

    @FXML
    private DatePicker date_DatePicker;

    ContextMenu tableViewContextMenu;

    private void closeWindow(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        this.resourceBundle = resources;
        notification_TableView.setEditable(true);
        tableViewContextMenuSetting();
        notification_TableView.setContextMenu(tableViewContextMenu);

        Integer [] hours = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        Integer [] minutes = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,
        24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52, 53,54,
                55,56,57,58,59};

        mode_ComboBox.setConverter(new StringConverter<NotificationMode>() {

            @Override
            public String toString(NotificationMode object) {
                return object.getDescription();
            }

            @Override
            public NotificationMode fromString(String string) {
                return NotificationMode.valueOf(string);
            }

        });

        mode_ComboBox.getItems().addAll(NotificationMode.values());

        mode_ComboBox.getSelectionModel().select(0);
        hours_ComboBox.getItems().addAll(hours);
        minutes_ComboBox.getItems().addAll(minutes);

        minutes_ComboBox.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                if (value == null) {
                    return "";
                } else {
                   if (value<10) return "0" + value.intValue();
                   return value.toString();
                }
            }

            @Override
            public Integer fromString(String s) {
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });

        hours_ComboBox.getSelectionModel().select(0);
        minutes_ComboBox.getSelectionModel().select(0);

        date_TableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        time_TableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        mode_TableColumn.setCellValueFactory(new PropertyValueFactory<>("mode"));


        mode_TableColumn.setCellFactory((param) -> new ComboBoxTableCell<Notification, NotificationMode>(new StringConverter<NotificationMode>() {

            @Override
            public String toString(NotificationMode object) {
                return object.getShortdescription();
            }

            @Override
            public NotificationMode fromString(String string) {
                return NotificationMode.valueOf(string);
            }

        }, NotificationMode.values()) {
            public void updateItem(NotificationMode item, boolean empty) {
            super.updateItem(item, empty);
            //onUtil.save(data, RuntimeDataHolder.PATH);
            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                switch (item) {
                    case UNREPEATABLE: setStyle("-fx-background-color: #6495ed; -fx-text-fill: white;"); break;
                    case DAILY:  setStyle("-fx-background-color: #50c878; -fx-text-fill: white;"); break;
                    case WEEKLY:  setStyle("-fx-background-color: #ff8800; -fx-text-fill: white;"); break;
                    case MONTHLY:  setStyle("-fx-background-color: #ff2400; -fx-text-fill: white;"); break;
                    case ANNUALLY:  setStyle("-fx-background-color: #229200; -fx-text-fill: white;"); break;
                    default: setStyle(""); break;
                }
            }
           // setFilters();
        }
        }
        ) ;


        notification_TableView.setItems((ObservableList<Notification>) data.getCurrentTask().getNotificationList());

//        date_TableColumn.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Assignee, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Assignee, String> t) {
//
//                        Assignee assignee= (Assignee) t.getTableView().getItems().get(t.getTablePosition().getRow());
//
//                        if (!assignee.equals(data.getRootAssignee())) {
//                            assignee.setName(t.getNewValue());
//                            return;
//                        }
//                        notification_TableView.setItems(data.);
//                        return;
//                    }
//                }
//        );
//
//
        notification_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               current = newSelection;
            }
            else {

            }
        });
//
//        notification_TableView.setItems(data.getAssignees());
    }

    public  void update(){

    }


    private boolean checkValues() {
        if (date_DatePicker.valueProperty().get()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("NCTM - Уведомления");
            alert.setHeaderText("Необходимо указать дату!");
            alert.showAndWait().ifPresent(rs -> {
            });
            return false;
        }

        return true;
    }

    public void add_Click(ActionEvent actionEvent) {
        if (checkValues()){

            data.getCurrentTask().addNotification(
                    mode_ComboBox.getSelectionModel().getSelectedItem(),
                    date_DatePicker.getValue(),
                    LocalTime.of(hours_ComboBox.getValue(), minutes_ComboBox.getValue()));
//            data.getAssignees().add(new Assignee(name_TextField.getText()));
//            name_TextField.clear();
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

    private void deleteTaskDialog(Notification assignee) {

        if (assignee!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("NTCM - Уведомление");
            alert.setHeaderText(new Formatter().format(
                    "Вы уверены, что хотите удалить уведомление %s?",
                    assignee.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).toString());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    data.getCurrentTask().getNotificationList().remove(current);
                   // JsonUtil.save(data, RuntimeDataHolder.PATH);
                }
                if (rs == ButtonType.CANCEL) {
                    return;
                }
            });
        }
    }
}
