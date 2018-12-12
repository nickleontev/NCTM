package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Task;
import com.netcracker.edu.util.DialogManager;
import com.netcracker.edu.util.RuntimeDataHolder;
import com.netcracker.edu.util.Tree;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
import java.lang.annotation.ElementType;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

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

    /**
     * <p>Method for opening the modal window "Создать проект" when you click on the button "Создать проект"</p>
     */
    public void createProjectClick(ActionEvent actionEvent) {
       createProjectStage = showModalWindow("Создать проект",actionEvent, createProjectStage, "/fxml/CreateProjectForm.fxml", "/styles/projectLabel.css");
    }

    /**
     * <p>Method for opening the modal window "Добавить задачу" when you click on the button "Добавить задачу"</p>
     */
    public void createTaskClick(ActionEvent actionEvent) {

        createTaskStage = showModalWindow("Добавить задачу", actionEvent, createTaskStage, "/fxml/CreateTaskForm.fxml", "/styles/projectLabel.css");// для ожидания закрытия окна
        tasks_TableView.setItems(data.getCurrent().agregate());
    }

    /**
     * <p>Method for opening the modal window "Добавить исполнителя" when you click on the menu item "Добавить исполнителя"</p>
     */
    public void createAssigneeClick(ActionEvent actionEvent) {
        System.out.println("Нажали кнопку создать контактное лицо");

        createAssigneeStage = showModalWindow("Добавить исполнителя", actionEvent, createAssigneeStage,  "/fxml/CreateAssigneeForm.fxml","");
    }

    /**
     * <p>Method for opening the modal window "Исполнители" when you click on the menu item "Исполнители"</p>
     */
    public void showAssignee(ActionEvent actionEvent) throws IOException{

       showAssigneeStage = showModalWindow("Исполнители", actionEvent, showAssigneeStage, "/fxml/ShowAssigneeForm.fxml","" );
    }

    /**
     * <p>Method for initializing and opening modal windows</p>
     *
     * @param title Name of window title
     * @param actionEvent Was needed to specify the parent window
     * @param stage To initialize a concrete stage for a child window.
     * @param fxmlpath Path to fxml source
     * @param css Path to css source. The style will not be set if the parameter is empty string.
     */
    private Stage showModalWindow(String title, ActionEvent actionEvent,Stage stage, String fxmlpath, String css) {
        if (stage == null){
            fxmlLoader = new FXMLLoader();
            try {
                stage = new Stage();
                fxmlLoader.setLocation(getClass().getResource(fxmlpath));
                fxmlEdit = fxmlLoader.load();
                stage.setResizable(false);
                Scene scene = new Scene(fxmlEdit);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                stage.setTitle(title);

                if (!css.equals("")) {
                    scene.getStylesheets().add(css);
                }
            }
            catch (IOException ex) {
                log.debug("Ошибка при создании компонентов окна пользовательского интерфейса");
            }
        }
        stage.showAndWait();
        return stage;
    }

    public void doIt() {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Изменить");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

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
