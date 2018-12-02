package com.netcracker.edu;

import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.util.RuntimeDataHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);
    private Root data = RuntimeDataHolder.getHolder();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

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

        stage.setTitle("Hello JavaFX and Maven");
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.setMaximized(true);
        stage.show();
    }
}
