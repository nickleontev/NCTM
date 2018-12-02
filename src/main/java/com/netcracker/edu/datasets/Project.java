package com.netcracker.edu.datasets;

import java.util.Date;
import java.util.List;

public class Project {
    private String summary;

    private String description;

    private Date created;

    private Date updated;

    private Date deadline;

    private List<Project> projectList;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

}
