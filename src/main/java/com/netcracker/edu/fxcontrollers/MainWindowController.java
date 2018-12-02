package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.util.RuntimeDataHolder;
import com.netcracker.edu.util.Tree;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    private Root data = RuntimeDataHolder.getHolder();

    @FXML
    private TreeView <Project> project_treeView;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private CreateProjectController createProjectController;



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

    private ObservableList<Project> backupList;

    private Stage mainStage;
    private Parent fxmlEdit;
    private ResourceBundle resourceBundle;

    //private EditDialogController editDialogController;



    public void createProjectClick(ActionEvent actionEvent) {
        System.out.println("Нажали клавишу");

        if (createProjectStage == null) {

            try {
                createProjectStage = new Stage();

                fxmlLoader.setLocation(getClass().getResource("/fxml/CreateProjectForm.fxml"));
               // fxmlLoader.setResources(ResourceBundle.getBundle(Main.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
                fxmlEdit = fxmlLoader.load();
                createProjectController = fxmlLoader.getController();

                Parent root = FXMLLoader.load(getClass().getResource("/fxml/CreateProjectForm.fxml"));
                // createProjectStage.setTitle(resourceBundle.getString("edit"));
                createProjectStage.setTitle("Создать проект");
//                createProjectStage.setMinHeight(150);
//                createProjectStage.setMinWidth(300);
                createProjectStage.setResizable(false);
                Scene scene = new Scene(fxmlEdit);
                scene.getStylesheets().add("/styles/projectLabel.css");
                createProjectStage.setScene(scene);
                createProjectStage.initModality(Modality.WINDOW_MODAL);
                createProjectStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        createProjectController.update();
        createProjectStage.showAndWait(); // для ожидания закрытия окна
    }

    public void createTaskClick(ActionEvent actionEvent) {
        doIt();

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

    public void doIt() {
        Tree tree = new Tree(project_treeView, data.getRoot().getProjectList(), data.getRoot());
        //tree.getTreeView();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resourceBundle = resources;
        doIt();

        //TreeItem<Project> rootItem = new TreeItem<Project>(data.getRoot());doIt();

        //rootItem.setExpanded(true);

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
