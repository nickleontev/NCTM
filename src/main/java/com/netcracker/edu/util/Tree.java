package com.netcracker.edu.util;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import com.netcracker.edu.fxmodel.Root;
import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Task;
import com.sun.javafx.scene.control.skin.TableCellSkin;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class Tree {

    private TreeView<Project> treeView ;
    private TableView<Task> tableView ;
    private Root data = RuntimeDataHolder.getHolder();

    private final List<Class<? extends Project>> itemTypes = Arrays.asList(
            Project.class
    );

    public Tree(TreeView tree, TableView tb, Label label, DatePicker datePicker, ObservableList<Project> accounts, ContextMenu contextMenu, Project root) {
        treeView = tree;
        tableView = tb;


        treeView.setContextMenu(contextMenu);
        TreeItem<Project> treeRoot = createItem(root);

        treeView.setRoot(treeRoot);
        treeView.setShowRoot(true);

        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent e) {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            EventTarget target = e.getTarget();
                            if (target instanceof TreeCell
                                    || ((Node) target).getParent() instanceof TreeCell) {
                                treeView.getContextMenu().hide();

                            } else {
                                // hide the context menu when click event is outside table row
                                treeView.getContextMenu().hide();
                            }
                        }
                    }
                });




        treeView.setCellFactory(tv -> {

            TreeCell<Project> cell = new TreeCell<Project>() {

                @Override
                protected void updateItem(Project item, boolean empty) {
                    super.updateItem(item, empty);
                    textProperty().unbind();
                    if (empty) {
                        setText(null);
                        itemTypes.stream().map(Tree.this::asPseudoClass)
                                .forEach(pc -> pseudoClassStateChanged(pc, false));
                    } else {
                        textProperty().bind(item.summaryProperty());
                        PseudoClass itemPC = asPseudoClass(item.getClass());
                        itemTypes.stream().map(Tree.this::asPseudoClass)
                                .forEach(pc -> pseudoClassStateChanged(pc, itemPC.equals(pc)));
                    }
                }
            };

            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered && (! cell.isEmpty())) {
                    //System.out.println("Mouse hover on "+cell.getItem().getSummary());
                }
            });


            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {

                    data.setCurrent(cell.getItem());
                    tableView.setItems(data.getCurrent().agregate());
                    label.setText(data.getCurrent().getSummary());
                    datePicker.setValue(data.getCurrent().getDeadline());

                    //System.out.println(data.getCurrentTask().getSummary());
                }

            });

            return cell ;
        });
    }

    public TreeView<Project> getTreeView() {
        return treeView ;
    }

    private TreeItem<Project> createItem(Project object) {

        // create tree item with children from game object's list:

        TreeItem<Project> item = new TreeItem<>(object);
        item.setExpanded(true);
        item.getChildren().addAll(object.getProjectList().stream().map(this::createItem).collect(toList()));

//        List<Project> list =object.getProjectList();
//
//        for (int i=0; i<list.size();i++) {
//            item.getChildren().addAll(createItem(list.get(i)));
//        }


        // update tree item's children list if game object's list changes:

        object.getProjectList().addListener((Change<? extends Project> c) -> {
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

    private PseudoClass asPseudoClass(Class<?> clz) {
        return PseudoClass.getPseudoClass(clz.getSimpleName().toLowerCase());
    }

}