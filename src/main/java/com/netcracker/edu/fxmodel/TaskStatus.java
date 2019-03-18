package com.netcracker.edu.fxmodel;

public enum TaskStatus   {
    OPEN("OPN", "Открыта"),
    POSTPONED("PND", "Отложена"),
    COMPLETED("CTD", "Завершена");

    private String id;
    private String description;

    private TaskStatus(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {return this.id;}
    public String getDescription() {return this.description;}
    public static TaskStatus getInstanceById(String id) {
        switch (id) {
            case "OPN": return TaskStatus.OPEN;
            case "PND": return TaskStatus.POSTPONED;
            case "CTD": return TaskStatus.COMPLETED;
        }
        return TaskStatus.OPEN;
    }
}
