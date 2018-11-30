package com.netcracker.edu.fxcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;

public class CreateProjectController
{
    private static final Logger log = LoggerFactory.getLogger(CreateProjectController.class);

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private Label messageLabel;



//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        this.resourceBundle = resources;
//        columnFIO.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
//        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
//        setupClearButtonField(txtSearch);
//        initListeners();
//        fillData();
//        initLoader();
//    }

//
//    private void initLoader() {
//        try {
//           // fxmlLoader.setLocation(getClass().getResource(FXML_EDIT));
//           // fxmlLoader.setResources(ResourceBundle.getBundle(Main.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
//            fxmlEdit = fxmlLoader.load();
//            //editDialogController = fxmlLoader.getController();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    private Stage createProjectStage;
    private Stage createTaskStage;

    private Stage mainStage;
    private Parent fxmlEdit;
    private ResourceBundle resourceBundle;
    private FXMLLoader fxmlLoader = new FXMLLoader();

    //private EditDialogController editDialogController;



    public void createProjectClick(ActionEvent actionEvent) {
        System.out.println("Нажали клавишу");

        if (createProjectStage == null) {

            try {
                createProjectStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/CreateProjectForm.fxml"));
                // createProjectStage.setTitle(resourceBundle.getString("edit"));
                createProjectStage.setTitle("Создать проект");
//                createProjectStage.setMinHeight(150);
//                createProjectStage.setMinWidth(300);
                createProjectStage.setResizable(false);
                createProjectStage.setScene(new Scene(root));
                createProjectStage.initModality(Modality.WINDOW_MODAL);
                createProjectStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        createProjectStage.showAndWait(); // для ожидания закрытия окна
    }

    public void createTaskClick(ActionEvent actionEvent) {

        if (createTaskStage == null) {

            try {
                createTaskStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/CreateTaskForm.fxml"));
                // createProjectStage.setTitle(resourceBundle.getString("edit"));
                createTaskStage.setTitle("Создать задачу");
//                createProjectStage.setMinHeight(150);
//                createProjectStage.setMinWidth(300);
                createTaskStage.setResizable(false);
                createTaskStage.setScene(new Scene(root));
                createTaskStage.initModality(Modality.WINDOW_MODAL);
                createTaskStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        createTaskStage.showAndWait(); // для ожидания закрытия окна
    }

    public void sayHello() {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        StringBuilder builder = new StringBuilder();

        if (!StringUtils.isEmpty(firstName)) {
            builder.append(firstName);
        }

        if (!StringUtils.isEmpty(lastName)) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }

        if (builder.length() > 0) {
            String name = builder.toString();
            log.debug("Saying hello to " + name);
            messageLabel.setText("Hello " + name);
        } else {
            log.debug("Neither first name nor last name was set, saying hello to anonymous person");
            messageLabel.setText("Hello mysterious person");
        }
    }

}
