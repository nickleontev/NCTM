package com.netcracker.edu.dao.impl.runtime;

import com.netcracker.edu.dao.interfaces.ProjectDAO;
import com.netcracker.edu.fxmodel.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Root {

    public Root() {
    }

   private Project root = new Project("", "", LocalDate.MIN);
   private Project current = root;


    public Project getCurrent() {
        return current;
    }

    public void setCurrent(Project current) {
        this.current = current;
    }
}