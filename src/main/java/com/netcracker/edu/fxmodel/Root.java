package com.netcracker.edu.fxmodel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


//@XmlRootElement (name = "root")
public class Root {

    public Root() {
//        rootAssignee = new Assignee("Вы");
//        assignees.add(rootAssignee);
    //    root = new Project("Проекты", "", null);
    }


    public void setRoot(Project root) {
        this.root = root;
    }

    private Project root = new Project("Проекты", "", null);
    private Project current = root;
   private Assignee rootAssignee;
   private Task currentTask;
  // private ArrayList<Assignee> assignees;// = new ArrayList<>();

    private ObservableList<Assignee> assignees =  FXCollections.observableArrayList();

   // @XmlElement (name = "rootProject")
    public Project getRoot() {
        return root;
    }

    //@XmlTransient
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

    public ObservableList<Assignee> getAssignees() {
        return assignees;
    }

    public void removeAssignee(Assignee assignee) {
        assignees.remove(assignee);
    }

    public Assignee getRootAssignee() {
        return rootAssignee;
    }
}
