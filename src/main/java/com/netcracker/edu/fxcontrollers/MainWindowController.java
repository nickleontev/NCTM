package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.NotificationManager;
import com.netcracker.edu.fxmodel.*;
import com.netcracker.edu.util.JsonUtil;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.event.ActionEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;

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
    private TableColumn <Task, TaskStatus> status_TableColumn;

    @FXML
    public TableColumn <Task, Assignee> assignee_TableColumn;

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

    @FXML
    private CheckBox showComplited_CheckBox;

    @FXML
    private CheckBox showNasted_CheckBox;

    ContextMenu treeViewContextMenu;

    ContextMenu tableViewContextMenu;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private CreateProjectController createProjectController;
    private CreateTaskController createTaskController;
    private CreateAssigneeController createAssigneeController;


    FilteredList<Task> filteredList = new FilteredList<> ((ObservableList<Task>)(data.getCurrent().getTaskList()));
    private Stage createProjectStage;
    private Stage createTaskStage;
    private Stage createAssigneeStage;
    private Stage showAssigneeStage;
    private Parent fxmlEdit;
    private ResourceBundle resourceBundle;

    public void deleteTaskClick(ActionEvent actionEvent) {
        deleteTaskDialog();
    }

    /**
     * <p>Method for opening the modal window "Создать проект" when you click on the button "Создать проект"</p>
     */
    public void createProjectClick(ActionEvent actionEvent) {
       showModalWindow("Создать проект",actionEvent, createProjectStage, "/fxml/CreateProjectForm.fxml", "/styles/projectLabel.css");
    }

    /**
     * <p>Method for opening the modal window "Добавить задачу" when you click on the button "Добавить задачу"</p>
     */
    public void createTaskClick(ActionEvent actionEvent) {

        showModalWindow("Добавить задачу", actionEvent, createTaskStage, "/fxml/CreateTaskForm.fxml", "/styles/projectLabel.css");// для ожидания закрытия окна
        setFilters();
    }

    /**
     * <p>Method for opening the modal window "Добавить исполнителя" when you click on the menu item "Добавить исполнителя"</p>
     */
    public void createAssigneeClick(ActionEvent actionEvent) {
        System.out.println("Нажали кнопку создать контактное лицо");

        showModalWindow("Добавить исполнителя", actionEvent, createAssigneeStage,  "/fxml/CreateAssigneeForm.fxml","");
    }

    /**
     * <p>Method for opening the modal window "Исполнители" when you click on the menu item "Исполнители"</p>
     */
    public void showAssignee(ActionEvent actionEvent) throws IOException{

       showModalWindow("Исполнители", actionEvent, showAssigneeStage, "/fxml/AssigneeForm.fxml","" );
    }

    public void showNotifications(ActionEvent actionEvent) {
        showModalWindow("Уведомления", actionEvent, null, "/fxml/NotificationForm.fxml","" );
    }

    /**
     * <p>Method for initial izing and opening modal windows</p>
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

    /**
     * <p>Method for updating "Right area" on window</p>
     */
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

        //loadPersonDataFromFile("ffff.xml");

        this.resourceBundle = resources;

        projectSummary_Label.setText(data.getCurrent().getSummary());

        tableViewSetting();
        checkBoxSetting();
        treeSetting();
        setFilters();
        fieldSetting();

    }

    /**
     * <p>Method of preparation contextMenu for treeView</p>
     */
    private void treeViewContextMenuSetting() {

        treeViewContextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Изменить");
        item1.setOnAction(event -> {
            if (data.getCurrent()!=data.getRoot()) {
                showModalWindow("Редактировать проект", null, createProjectStage, "/fxml/EditProjectForm.fxml", "/styles/projectLabel.css");
            }
        });
        MenuItem item2 = new MenuItem("Удалить");
        item2.setOnAction(event -> {

            if (data.getCurrent() == data.getRoot()) {
                //Невозможно удалить корневой элемент
            }
            else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление проекта");

                String proj = data.getCurrent().getSummary();
                Formatter formatter = new Formatter();

                if (!data.getCurrent().getProjectList().isEmpty() || !data.getCurrent().getTaskList().isEmpty()) {
                    formatter.format("Вы уверены, что хотите удалить проект '%s'? Все подпроекты и задачи проекта '%s' будут удалены.",proj, proj);
                } else  formatter.format("Вы уверены, что хотите удалить проект '%s'?", proj);

                alert.setHeaderText(formatter.toString());
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        Project parent = data.getCurrent().getParentProject();
                        parent.removeSubProject(data.getCurrent());
                        project_treeView.getSelectionModel().select(0);
                        data.setCurrent(data.getRoot());
                        updateTaskInfo(null);
                        setFilters();
                        projectSummary_Label.setText(data.getCurrent().getSummary());
                        deadline_DatePicker.setValue(data.getCurrent().getDeadline());
                    }
                    if (rs == ButtonType.CANCEL) {
                        return;
                    }
                });
            }
        });
        // Add MenuItem to ContextMenu
        treeViewContextMenu.getItems().addAll(item1, item2);
    }


    /**
     * <p>Method of preparation contextMenu for treeView</p>
     */
    private void tableViewContextMenuSetting() {

        tableViewContextMenu = new ContextMenu();

        MenuItem item = new MenuItem("Удалить");
        item.setOnAction(event -> {
            deleteTaskDialog();
        });
        // Add MenuItem to ContextMenu
        tableViewContextMenu.getItems().addAll(item);
    }

    private void deleteTaskDialog() {
        if (data.getCurrentTask()!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление задачи");
            alert.setHeaderText(new Formatter().format("Вы уверены, что хотите удалить задачу '%s'?", data.getCurrentTask().getSummary()).toString());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    data.getCurrent().removeTask(data.getCurrentTask());
                    data.setCurrentTask(null);
                    //JsonUtil.save(data, RuntimeDataHolder.PATH);
                    updateTaskInfo(null);
                    setFilters();
                }
                if (rs == ButtonType.CANCEL) {
                    return;
                }
            });
        }
    }

    /**
     * <p>Method of preparation treeView</p>
     */
    private void treeSetting(){

        treeViewContextMenuSetting();

        project_treeView.setContextMenu(treeViewContextMenu);
        TreeItem<Project> treeRoot = createItem(data.getRoot());

        project_treeView.setRoot(treeRoot);
        project_treeView.setShowRoot(true);

        project_treeView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent e) {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            EventTarget target = e.getTarget();
                            if (target instanceof TreeCell
                                    || ((Node) target).getParent() instanceof TreeCell) {
                                project_treeView.getContextMenu().hide();

                            } else {
                                project_treeView.getContextMenu().hide();
                            }
                        }
                    }
                });

        project_treeView.setCellFactory(tv -> {

            TreeCell<Project> cell = new TreeCell<Project>() {

                @Override
                protected void updateItem(Project item, boolean empty) {
                    super.updateItem(item, empty);
                    textProperty().unbind();
                    if (empty) {
                        setText(null);
                        itemTypes.stream().map(MainWindowController.this::asPseudoClass)
                                .forEach(pc -> pseudoClassStateChanged(pc, false));
                    } else {
                        textProperty().bind(item.summaryProperty());
                        PseudoClass itemPC = asPseudoClass(item.getClass());
                        itemTypes.stream().map(MainWindowController.this::asPseudoClass)
                                .forEach(pc -> pseudoClassStateChanged(pc, itemPC.equals(pc)));
                    }
                }
            };

            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered && (! cell.isEmpty())) {
                }
            });


            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {
                    data.setCurrent(cell.getItem());
                    setFilters();
                    projectSummary_Label.setText(data.getCurrent().getSummary());
                    deadline_DatePicker.setValue(data.getCurrent().getDeadline());
                }
            });
            return cell ;
        });
    }

    /**
     * <p>Method of preparation tasks_TableView</p>
     */
    private void tableViewSetting() {
        //Делаем tableView редактируемым
        tasks_TableView.setEditable(true);
        //Устанавливаем контекстное меню
        tableViewContextMenuSetting();
        tasks_TableView.setContextMenu(tableViewContextMenu);

        //Привязка tableView к данным
        task_TableColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("summary"));
        deadline_TableColumn.setCellValueFactory(new PropertyValueFactory<Task,LocalDate>("deadline"));
        project_TableColumn.setCellValueFactory(new PropertyValueFactory<Task, Project>("parent"));
        days_TableColumn.setCellValueFactory(new PropertyValueFactory<Task, Long>("dateDifference"));
        status_TableColumn.setCellValueFactory(new PropertyValueFactory<Task, TaskStatus>("status"));
        assignee_TableColumn.setCellValueFactory(new PropertyValueFactory<Task, Assignee>("assignee"));

        //Фабрика для ячеек столбца "Статус" tableView
        //Задает в каждой ячейке comboBox
        //Расскрашивает ячейку в зависимости от статуса задачи
        status_TableColumn.setCellFactory((param) -> new ComboBoxTableCell<Task, TaskStatus>(new StringConverter<TaskStatus>() {

            @Override
            public String toString(TaskStatus object) {
                return object.getDescription();
            }

            @Override
            public TaskStatus fromString(String string) {
                return TaskStatus.valueOf(string);
            }

        }, TaskStatus.values()) {  public void updateItem(TaskStatus item, boolean empty) {
            super.updateItem(item, empty);
            //onUtil.save(data, RuntimeDataHolder.PATH);
            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                switch (item) {
                    case OPEN: setStyle("-fx-background-color: #6495ed; -fx-text-fill: white;"); break;
                    case COMPLETED:  setStyle("-fx-background-color: #50c878; -fx-text-fill: white;"); break;
                    case POSTPONED:  setStyle("-fx-background-color: #ff8800; -fx-text-fill: white;"); break;
                    default: setStyle(""); break;
                }
            }
            setFilters();
        }}) ;

        assignee_TableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(data.getAssignees()));
        assignee_TableColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, Assignee>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, Assignee> t) {
                        ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAssignee(t.getNewValue());
                       // JsonUtil.save(data, RuntimeDataHolder.PATH);
                    };
                }
        );

        //Событие фокуса над tasks_TableView
        tasks_TableView.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
            }
        });

        //Событие выбора конкретной строки таблицы
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
//                        if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY  && event.getClickCount() == 1) {
//                        }
                    }
            );
            return row ;
        });
    }

    /**
     * <p>Method of preparation  showComplited_CheckBox & showNasted_CheckBox</p>
     */
    private void checkBoxSetting () {
        showComplited_CheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                setFilters();
            }
        });

        showNasted_CheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                setFilters();
            }
        });
    }

    /**
     * <p>Method for preparation "right-area" fields</p>
     */

    //!!!! Проверит на наличие выбранной задачи
    private void fieldSetting () {

        taskSummary_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!=newValue && !newValue.equals("")) data.getCurrentTask().setSummary(newValue);
            //JsonUtil.save(data, RuntimeDataHolder.PATH);
        });

        taskDescription_TextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!=newValue && !newValue.equals("")) data.getCurrentTask().setDescription(newValue);
           // JsonUtil.save(data, RuntimeDataHolder.PATH);
        });

        taskDeadline_DatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue!=newValue && newValue!=null) {
                data.getCurrentTask().setDeadline(newValue);
                tasks_TableView.refresh();
               // JsonUtil.save(data, RuntimeDataHolder.PATH);
            }
        });
    }

    /**
     * <p>Method for setting filters to tableView</p>
     */
    private void setFilters(){
        ObservableList list;

        if (showNasted_CheckBox.isSelected()) list = data.getCurrent().agregateNasted();
        else list = (ObservableList) data.getCurrent().getTaskList();

        if (!showComplited_CheckBox.isSelected()) list = list.filtered((Predicate<Task>) t -> {
            if (t.getStatus().equals(TaskStatus.COMPLETED)) return false;
            return true;
        });

        tasks_TableView.setItems(list);
    }

    /**
     * <p>Util method for treeView</p>
     */
    private TreeItem<Project> createItem(Project object) {
        TreeItem<Project> item = new TreeItem<>(object);
        item.setExpanded(true);
        item.getChildren().addAll(object.getProjectList().stream().map(this::createItem).collect(toList()));

        ( (ObservableList<Project>) object.getProjectList()).addListener((ListChangeListener.Change<? extends Project> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    item.getChildren().addAll(c.getAddedSubList().stream().map(this::createItem).collect(toList()));
                }
                if (c.wasRemoved()) {
                    item.getChildren().removeIf(treeItem -> c.getRemoved().contains(treeItem.getValue()));
                }
            }
        });
        return item ;
    }

    /**
     * <p>Util collection for treeView</p>
     */
    private final List<Class<? extends Project>> itemTypes = Arrays.asList(Project.class);

    /**
     * <p>Util method for treeView</p>
     */
    private PseudoClass asPseudoClass(Class<?> clz) {
        return PseudoClass.getPseudoClass(clz.getSimpleName().toLowerCase());
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        File file = new File("ffff.xml");
        try {
            JAXBContext context = JAXBContext.newInstance(Project.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

//
//            // Маршаллируем и сохраняем XML в файл.
            m.marshal(data.getRoot(), file);
//            Project root = loadPersonDataFromFile(new File("ffff.xml"));
//            NotificationManager notificationManager = new NotificationManager();
//            List<Notification> n = notificationManager.getAllNotification(root);
//            LocalDate ld2019 = LocalDate.of(2019, 3, 1);
//            LocalDate ld2020 = LocalDate.of(2020, 3, 1);
//            int r = Year.of(2019).length();
//            int r2 = Year.of(2020).length();
//            long d = DAYS.between(LocalDate.now(),LocalDate.of(2019, 3, 12));
            System.out.println("");
        } catch (Exception e) { // catches ANY exception

            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public Project loadPersonDataFromFile(File file) {
        Project wrapper = null;
        try {
            JAXBContext context = JAXBContext
                    .newInstance(Project.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
             wrapper = (Project) um.unmarshal(file);


        } catch (Exception e) { // catches ANY exception
            //e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Данные не найдены");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
        return wrapper;
    }


    public Project loadPersonDataFromFile(String path) {
        File file = new File(path);
        Project wrapper = null;
        try {
            JAXBContext context = JAXBContext
                    .newInstance(Project.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            wrapper = (Project) um.unmarshal(file);
            data.setRoot(wrapper);



        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
        return wrapper;
    }

}


