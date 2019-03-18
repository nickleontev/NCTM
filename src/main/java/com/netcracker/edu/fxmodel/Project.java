package com.netcracker.edu.fxmodel;

import com.netcracker.edu.util.xmladapters.LocalDateAdapter;
import com.netcracker.edu.util.xmladapters.ProjectAdapter;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@XmlJavaTypeAdapter(value = ProjectAdapter.class)
@XmlRootElement(name = "root")
public class Project {

    private static final Logger log = LoggerFactory.getLogger(Project.class);

    private  StringProperty summary;
    private  StringProperty description;
    private  ObjectProperty<LocalDate> created;
    private  ObjectProperty<LocalDate> updated;
    private  ObjectProperty<LocalDate> deadline;
    private ObservableList<Project> projectList;

    private ObservableList<Task> taskList;


   private Project parentProject;

    /**
     * Конструктор по умолчанию.
     */
    public Project() {

        this(null, null, null);

       this.projectList = FXCollections.observableArrayList();
       this.taskList = FXCollections.observableArrayList();

    }



    public Project(String summary, String description, LocalDate deadline) {
        this.summary = new SimpleStringProperty(summary);
        this.description = new SimpleStringProperty(description);
        this.created = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.updated = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.deadline = new SimpleObjectProperty<LocalDate>(deadline);
        this.projectList = FXCollections.observableArrayList();
        this.taskList = FXCollections.observableArrayList();
    }

    public Project(String summary, String description, LocalDate deadline, Project parentProject) {
//        this.summary = new SimpleStringProperty(summary);
//        this.description = new SimpleStringProperty(description);
//        this.created = new SimpleObjectProperty<LocalDate>(LocalDate.now());
//        this.updated = new SimpleObjectProperty<LocalDate>(LocalDate.now());
//        this.deadline = new SimpleObjectProperty<LocalDate>(deadline);
//        this.projectList = FXCollections.observableArrayList();
//        this.taskList = FXCollections.observableArrayList();

        this(summary, description, deadline);

        this.parentProject = parentProject;

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

    //@XmlJavaTypeAdapter(ProjectAdapter.class)
    @XmlElements({ @XmlElement(name = "project", type = Project.class) })
    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList.addAll(projectList);
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

    public void setTaskList(List<Task> taskList) {
        this.taskList.addAll(taskList);
    }

    @XmlTransient
    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    @XmlElements({ @XmlElement(name = "task", type = Task.class) })
    public List<Task> getTaskList() {
        return taskList;
    }

    public ObservableList<Task> agregateNasted() {
        ObservableList<Task> observableList = FXCollections.observableArrayList();
        observableList.addAll(this.taskList);

        for (int i = 0; i<projectList.size(); i++) {
            observableList.addAll(projectList.get(i).agregateNasted());
        }
        return observableList;
    }

    public boolean addSubProject(Project project) {

        try {
            project.setParentProject(this);
            projectList.add(project);
            return true;
        }
        catch (Exception ex) {
            log.debug("Exception while adding the project");
            return false;
        }
    }

    public boolean removeSubProject (Project project) {

        try {
            projectList.remove(project);
            return true;
        }
        catch (Exception ex) {
            log.debug("Exception while removing the project");
            return false;
        }
    }

    public boolean addTask(Task task) {

        try {
            task.setParent(this);
            taskList.add(task);
            return true;
        }
        catch (Exception ex) {
            log.debug("Exception while adding the project");
            return false;
        }
    }

    public boolean removeTask (Task task) {

        try {
            taskList.remove(task);
            return true;
        }
        catch (Exception ex) {
            log.debug("Exception while removing the project");
            return false;
        }
    }


    @Override
    public String toString() {
        return summary.getValue();
    }
}
