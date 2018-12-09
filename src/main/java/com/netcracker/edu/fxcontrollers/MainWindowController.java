package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Task;
import com.netcracker.edu.util.RuntimeDataHolder;
import com.netcracker.edu.util.Tree;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    private Root data = RuntimeDataHolder.getHolder();

    @FXML
    private TreeView <Project> project_treeView;

    @FXML
    private TableView<Task> tasks_TableView;

    @FXML
    private TableColumn <Task, String> task_TableColumn;

    @FXML
    private TableColumn <Task, LocalDate> deadline_TableColumn;

    @FXML
    private TableColumn <Task, Project> project_TableColumn;

    @FXML
    private TableColumn <Task, Long> days_TableColumn;

    @FXML
    private Label projectSummary_Label;

    @FXML
    private TextField taskSummary_TextField;

    @FXML
    private TextArea taskDescription_TextArea;

    @FXML
    private DatePicker deadline_DatePicker;

    @FXML
    private DatePicker taskDeadline_DatePicker;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private CreateProjectController createProjectController;
    private CreateTaskController createTaskController;
    private CreateAssigneeController createAssigneeController;
    private ShowAssigneeStageController showAssigneeStageController;


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
    private Stage createAssigneeStage;
    private Stage showAssigneeStage;

    private ObservableList<Project> backupList;

    private Stage mainStage;
    private Parent fxmlEdit;
    private ResourceBundle resourceBundle;

    //private EditDialogController editDialogController;



    public void createProjectClick(ActionEvent actionEvent) {
        openProjectForm(actionEvent,"Создание проекта");
    }


   private void openProjectForm(ActionEvent actionEvent, String title) {

       if (createProjectStage == null) {
           fxmlLoader = new FXMLLoader();
           try {
               createProjectStage = new Stage();
               fxmlLoader.setLocation(getClass().getResource("/fxml/CreateProjectForm.fxml"));
               fxmlEdit = fxmlLoader.load();
               createProjectController = fxmlLoader.getController();
               createProjectStage.setResizable(false);
               Scene scene = new Scene(fxmlEdit);
               scene.getStylesheets().add("/styles/projectLabel.css");
               createProjectStage.setScene(scene);
               createProjectStage.initModality(Modality.WINDOW_MODAL);
               createProjectStage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

           } catch (IOException e) {
               e.printStackTrace();
           }
       }

        createProjectController.update();
        createProjectStage.showAndWait(); // для ожидания закрытия окна
    }

    public void createTaskClick(ActionEvent actionEvent) {

        fxmlLoader = new FXMLLoader();
        if (createTaskStage == null) {

            try {
                createTaskStage = new Stage();

                fxmlLoader.setLocation(getClass().getResource("/fxml/CreateTaskForm.fxml"));
                // fxmlLoader.setResources(ResourceBundle.getBundle(Main.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
                fxmlEdit = fxmlLoader.load();
                createTaskController = fxmlLoader.getController();

//                Parent root = FXMLLoader.load(getClass().getResource("/fxml/CreateProjectForm.fxml"));
                // createProjectStage.setTitle(resourceBundle.getString("edit"));
                createTaskStage.setTitle("Создать задачу");
//                createProjectStage.setMinHeight(150);
//                createProjectStage.setMinWidth(300);
                createTaskStage.setResizable(false);
                Scene scene = new Scene(fxmlEdit);
                scene.getStylesheets().add("/styles/projectLabel.css");
                createTaskStage.setScene(scene);
                createTaskStage.initModality(Modality.WINDOW_MODAL);
                createTaskStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        createTaskStage.showAndWait(); // для ожидания закрытия окна
        createTaskController.update();
        tasks_TableView.setItems(data.getCurrent().agregate());
    }
    public void createAssigneeClick(ActionEvent actionEvent) {
        System.out.println("Нажали кнопку создать контактное лицо");
        fxmlLoader = new FXMLLoader();

        if (createAssigneeStage == null) {

            try {
                createAssigneeStage = new Stage();
                fxmlLoader.setLocation(getClass().getResource("/fxml/CreateAssigneeForm.fxml"));
                fxmlEdit = fxmlLoader.load();
                createAssigneeController = fxmlLoader.getController();
                createAssigneeStage.setTitle("Создать контактное лицо");
                createAssigneeStage.setResizable(false);
                Scene scene = new Scene(fxmlEdit);
                scene.getStylesheets().add("/styles/projectLabel.css");
                createAssigneeStage.setScene(scene);
                createAssigneeStage.initModality(Modality.WINDOW_MODAL);
                //createAssigneeStage.initOwner(((Node)actionEvent.getSource())getScene().getWindow()); *********** Не могу получить владельца-сцену, т.к. MenuItem не является дочерним классом от Node
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        createAssigneeController.update();
        createAssigneeStage.showAndWait(); // для ожидания закрытия окна
    }

    public void showAssignee() throws IOException{
        fxmlLoader = new FXMLLoader();
         if (showAssigneeStage == null){

                 showAssigneeStage = new Stage();
                 fxmlLoader.setLocation(getClass().getResource("/fxml/ShowAssigneeForm.fxml"));
                 fxmlEdit = fxmlLoader.load();
                 showAssigneeStageController = fxmlLoader.getController();
                 showAssigneeStage.setTitle("Список контактов");
                 Scene scene = new Scene(fxmlEdit);
                 scene.getStylesheets().add("/styles/projectLabel.css");
                 showAssigneeStage.setScene(scene);
                 showAssigneeStage.initModality(Modality.WINDOW_MODAL);
                 showAssigneeStage.showAndWait();
                 showAssigneeStageController.initializeData();
         }
    }

    public void doIt() {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Изменить");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                openProjectForm(event,"Редактирование проекта");
            }
        });
        MenuItem item2 = new MenuItem("Удалить");
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (data.getCurrent() == data.getRoot()) {
                    //Невозможно удалить корневой элемент
                }
                else {
                    Project parent = data.getCurrent().getParentProject();
                    parent.removeSubProject(data.getCurrent());
                    project_treeView.getSelectionModel().select(0);
                    data.setCurrent(data.getRoot());
                    updateTaskInfo(null);

                    tasks_TableView.setItems(data.getCurrent().agregate());
                    projectSummary_Label.setText(data.getCurrent().getSummary());
                    deadline_DatePicker.setValue(data.getCurrent().getDeadline());
                }
            }
        });

        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2);



        Tree tree = new Tree(project_treeView, tasks_TableView, projectSummary_Label, deadline_DatePicker, data.getRoot().getProjectList(), contextMenu, data.getRoot());
        //tree.getTreeView();
    }


    private void updateTaskInfo(Task task) {
        if (task!=null){
            taskSummary_TextField.setText(task.getSummary());
            taskDescription_TextArea.setText(task.getDescription());
            taskDeadline_DatePicker.setValue(task.getDeadline());
        }
        else {
            taskSummary_TextField.setText("");
            taskDescription_TextArea.setText("");
            taskDeadline_DatePicker.getEditor().clear();
            taskDeadline_DatePicker.setValue(null);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectSummary_Label.setText(data.getCurrent().getSummary());


        try{
            data.onStart();
        } catch (IOException ioEx) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
//            Node source = (Node) actionEvent.getSource();
//            Stage stage = (Stage) source.getScene().getWindow();
//            dialog.initOwner(stage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("Импорт контактов при первом запуске"));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        } catch (ClassNotFoundException cnfEx){

        }

        this.resourceBundle = resources;
        task_TableColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("summary"));
        deadline_TableColumn.setCellValueFactory(new PropertyValueFactory<Task,LocalDate>("deadline"));
        project_TableColumn.setCellValueFactory(new PropertyValueFactory<Task, Project>("parent"));
        days_TableColumn.setCellValueFactory(new PropertyValueFactory<Task, Long>("dateDifference"));



//        //Если фокус находится над taskSummary_TextField
//        taskSummary_TextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if (!newVal) {
//                data.getCurrentTask().setSummary(taskSummary_TextField.getText());
//            }
//        });

        taskSummary_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!=newValue && !newValue.equals("")) data.getCurrentTask().setSummary(newValue);
        });

        taskDescription_TextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!=newValue && !newValue.equals("")) data.getCurrentTask().setDescription(newValue);
        });

        taskDeadline_DatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!=newValue && newValue!=null) {
                data.getCurrentTask().setDeadline(newValue);
                tasks_TableView.refresh();

            }
        });

//        taskDeadline_DatePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
//            if (oldValue!=newValue && !newValue.equals("")) data.getCurrentTask().setDeadline(newValue);
//        });

        //Если фокус находится над tasks_TableView
        tasks_TableView.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
//               if (data.getCurrent() == tasks_TableView.getSelectionModel().getSelectedItem().getParent()) {
//
//               }
            }
        });


        tasks_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Task task = tasks_TableView.getSelectionModel().getSelectedItem();
                data.setCurrentTask(task);
                updateTaskInfo(task);
            }
            else {
                updateTaskInfo(null);
                data.setCurrentTask(null);
            }
        });


                tasks_TableView.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {

                    //data.setCurrentTask(row.getItem());
//                    taskSummary_Text.textProperty().bind(data.getCurrentTask().summaryProperty());
//                    taskDescription_TextArea.textProperty().bind(data.getCurrentTask().descriptionProperty());
//                    taskSummary_TextField.setText(row.getItem().getSummary());
//                   taskDescription_TextArea.setText(row.getItem().getDescription());


                }
            }

            );
            return row ;
        });

        tasks_TableView.setItems(data.getCurrent().agregate());



        doIt();

        //TreeItem<Project> rootItem = new TreeItem<Project>(data.getRoot());doIt();

        //rootItem.setExpanded(true);

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
