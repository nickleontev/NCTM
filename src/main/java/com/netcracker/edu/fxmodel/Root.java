package com.netcracker.edu.fxmodel;

import com.netcracker.edu.dao.interfaces.ProjectDAO;
import com.netcracker.edu.datasets.Assignee;
import com.netcracker.edu.fxmodel.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Root{

    public Root() {
    }

   private Project root = new Project("Проекты", "", LocalDate.MIN);
   private Project current = root;
   private ArrayList<Assignee> assignees;// = new ArrayList<>();

    public Project getRoot() {
        return root;
    }

    public Project getCurrent() {
        return current;
    }

    public void setCurrent(Project current) {
        this.current = current;
    }

    public List<Assignee> getAssignees() {
        return assignees;
    }

    public void setCurrentAssignee(Assignee assignee){
        assignees.add(assignee);
    }

    public ObservableList<Assignee> getObservableListAssignees(){
        //ObservableList<Assignee> observableListAssignees = (ObservableList)assignees;//FXCollections.observableArrayList();
        //observableListAssignees = (ObservableList)assignees;
        return FXCollections.observableArrayList(assignees);
    }

    public void onStart() throws IOException, ClassNotFoundException{
        assignees = (ArrayList<Assignee>) readAssignee();
    }

    public void onClose() throws IOException{
        writeAssignee();
    }

    public void update() throws IOException{

        writeAssignee();
    }

    private void writeAssignee() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("assignee.data"));
        oos.writeObject(assignees);
    }
    private Object readAssignee() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("assignee.data"));
        return ois.readObject();
    }
}
