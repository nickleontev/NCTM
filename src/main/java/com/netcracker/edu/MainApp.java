package com.netcracker.edu;

import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.util.JsonUtil;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class MainApp extends Application {

    public static final String PATH = "data.json";
    private Root data = RuntimeDataHolder.getHolder();

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private static Root load (){
        Root root = JsonUtil.load(PATH);
        if (root==null) {
            root = new Root();
            JsonUtil.save(root,PATH);
        }
        return root;
    }

    public void start(Stage stage) throws Exception {

        //RuntimeDataHolder.setHolder(load());
        log.info("Starting Hello JavaFX and Maven demonstration application");

        String fxmlFile = "/fxml/MainWindow.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();

        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        StackPane root = new StackPane();
        //root.setPadding(new Insets(5));
        //Tree tree = new Tree(data.getRoot().getProjectList(), data.getRoot());
        //root.getChildren().add(tree.getTreeView());
        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 400, 200);
        // scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("NCTM");
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.setMaximized(true);



        stage.show();

        Thread manager = new Thread(new NotificationManager(),"MyThread");
        manager.start();
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
