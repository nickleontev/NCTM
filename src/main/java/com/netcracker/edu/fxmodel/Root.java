package com.netcracker.edu.fxmodel;

import com.netcracker.edu.dao.interfaces.ProjectDAO;
import com.netcracker.edu.datasets.Assignee;
import com.netcracker.edu.fxmodel.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Root {

    public Root() {
    }

   private Project root = new Project("Проекты", "", LocalDate.MIN);
   private Project current = root;
   private ObservableList<Assignee> assignees = FXCollections.observableArrayList();

    public Project getRoot() {
        return root;
    }

    public Project getCurrent() {
        return current;
    }

    public void setCurrent(Project current) {
        this.current = current;
    }
    public void setCurrentAssignee(Assignee assignee){
        assignees.add(assignee);
    }

    public ObservableList<Assignee> getAssignees(){
        return assignees;
    }

    public String[] getNamesAssignees(){
        String[] names = new String[assignees.size()];
        for (int i = 0; i < assignees.size(); i ++){
            names[i] = assignees.get(i).getFullName();
        }
        return names;
    }
}
