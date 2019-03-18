package com.netcracker.edu.fxmodel;

import com.netcracker.edu.util.xmladapters.LocalDateAdapter;
import com.netcracker.edu.util.xmladapters.TaskStatusAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


public class Task {

   // private static final Logger log = LoggerFactory.getLogger(Task.class);

    private  StringProperty summary;
    private  StringProperty description;
    private  ObjectProperty<LocalDate> created;
    private  ObjectProperty<LocalDate> updated;
    private  ObjectProperty<LocalDate> deadline;
    private ObjectProperty<TaskStatus> status;
    private ObjectProperty<Assignee> assignee;

    //private ObservableList<SubTask> subTasks;

    private ObservableList<Notification> notificationList;
    private ObjectProperty<Project>  parent;



    /**
     * Конструктор по умолчанию.
     */
    public Task() {
        this (null,null,null,null, null);
        this.notificationList = FXCollections.observableArrayList();
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


    public Task(String summary, String description, LocalDate deadline, Project parentProject, Assignee assignee) {
        this.summary = new SimpleStringProperty(summary);
        this.description = new SimpleStringProperty(description);
        this.created = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.updated = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.deadline = new SimpleObjectProperty<LocalDate>(deadline);
        this.parent = new SimpleObjectProperty<Project>(parentProject);
        this.status = new SimpleObjectProperty<>(TaskStatus.OPEN);
        this.assignee = new SimpleObjectProperty<Assignee>(assignee);
        this.notificationList = FXCollections.observableArrayList();

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

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getCreated() {
        return created.get();
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    public ObjectProperty<LocalDate> createdProperty() {
        return created;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getUpdated() {
        return updated.get();
    }

    public void setUpdated(LocalDate updated) {
        this.updated.set(updated);
    }

    public ObjectProperty<LocalDate> updatedProperty() {
        return updated;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
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

    @XmlTransient
    public Project getParent() {
        return parent.get();
    }

    public ObjectProperty<Project> parentProperty() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent.set(parent);
    }

    @XmlTransient
    public Assignee getAssignee() {
        return assignee.get();
    }

    public ObjectProperty<Assignee> assigneeProperty() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee.set(assignee);
    }

    public Long getDateDifference() {

        if (getDeadline()!=null) {
            Long diff = DAYS.between(LocalDate.now(), getDeadline());
            return diff > 0 ? diff : 0;
        }else  return  Long.valueOf(0);
    }

    @XmlJavaTypeAdapter(value = TaskStatusAdapter.class)
    public TaskStatus getStatus() {
        return status.get();
    }

    public ObjectProperty<TaskStatus> statusProperty() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status.set(status);
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    @XmlElements({ @XmlElement(name = "notification", type = Notification.class) })
    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList.addAll(notificationList);
    }

    //    public StringProperty parentProjectProperty() {
//        return new SimpleStringProperty(getParentProject().getName());
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

    public void addNotification(NotificationMode mode, LocalDate date, LocalTime time) {
        getNotificationList().add(new Notification(mode,date,time, this));
    }

    public void removeNotification(Notification notification) {
        getNotificationList().remove(notification);
    }

    @Override
    public String toString() {
        return summary.getValue();
    }
}
