package com.netcracker.edu.fxmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;


public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    private  StringProperty summary;
    private  StringProperty description;
    private  ObjectProperty<LocalDate> created;
    private  ObjectProperty<LocalDate> updated;
    private  ObjectProperty<LocalDate> deadline;

    //private ObservableList<SubTask> subTasks;
    private ObjectProperty<Project>  parent;



    /**
     * Конструктор по умолчанию.
     */
    public Task() {

    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param
     * @param
     */

//    public Task(String summary, String description, LocalDate deadline) {
//        this.summary = new SimpleStringProperty(summary);
//        this.description = new SimpleStringProperty(description);
//        this.created = new SimpleObjectProperty<LocalDate>(LocalDate.now());
//        this.updated = new SimpleObjectProperty<LocalDate>(LocalDate.now());
//        this.deadline = new SimpleObjectProperty<LocalDate>(deadline);
//        this.parent = new SimpleObjectProperty<Project>(parentProject);
//    }


    public Task(String summary, String description, LocalDate deadline, Project parentProject) {
        this.summary = new SimpleStringProperty(summary);
        this.description = new SimpleStringProperty(description);
        this.created = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.updated = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.deadline = new SimpleObjectProperty<LocalDate>(deadline);
        this.parent = new SimpleObjectProperty<Project>(parentProject);;

    }
    public String getSummary() {
        return summary.get();
    }

    public void setSummary(String summary) {
        this.summary.set(summary);
    }
    public StringProperty summaryProperty() {
        return summary;
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    public ObjectProperty<LocalDate> createdProperty() {
        return created;
    }

    public LocalDate getUpdated() {
        return updated.get();
    }

    public void setUpdated(LocalDate updated) {
        this.updated.set(updated);
    }

    public ObjectProperty<LocalDate> updatedProperty() {
        return updated;
    }

    public LocalDate getDeadline() {
        return deadline.get();
    }

    public ObjectProperty<LocalDate> deadlineProperty() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline.set(deadline);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Project getParent() {
        return parent.get();
    }

    public ObjectProperty<Project> parentProperty() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent.set(parent);
    }

    public Long getDateDifference() {

        Long diff = DAYS.between(LocalDate.now(), getDeadline());

        return diff>0 ? diff: 0;
    }

//    public StringProperty parentProjectProperty() {
//        return new SimpleStringProperty(getParentProject().getSummary());
//    }

//    public boolean add (SubTask subTask) {
//
//        try {
//            subTasks.setParentProject(this);
//            subTasks.add(project);
//            return true;
//        }
//        catch (Exception ex) {
//            log.debug("Exception while adding the project");
//            return false;
//        }
//    }
//
//    public boolean remove (SubTask subTask) {
//
//        try {
//            subTasks.remove(subTask);
//            return true;
//        }
//        catch (Exception ex) {
//            log.debug("Exception while removing the project");
//            return false;
//        }
//    }

    @Override
    public String toString() {
        return summary.getValue();
    }
}
