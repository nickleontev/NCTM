package com.netcracker.edu.fxmodel;

import com.netcracker.edu.dao.interfaces.ProjectDAO;
import com.netcracker.edu.fxmodel.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Root {

    public Root() {
    }

   private Project root = new Project("Проекты", "", null);
   private Project current = root;
   private Task currentTask = new Task();

    public Project getRoot() {
        return root;
    }

    public Project getCurrent() {
        return current;
    }

    public void setCurrent(Project current) {
        this.current = current;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
}
