package com.netcracker.edu.fxmodel;

public enum NotificationMode {
    UNREPEATABLE("UNREPEAT", "Не повторять", "Один раз"),
    DAILY("DAILY", "Повторять каждый день", "Каждый день"),
    WEEKLY("WEEK", "Повторять каждую неделю", "Каждую неделю"),
    MONTHLY("MONTH", "Повторять каждый месяц", "Каждый месяц"),
    ANNUALLY("ANNUAL", "Повторять каждый год", "Каждый год");

    private String id;
    private String description;
    private String shortdescription;

    private NotificationMode(String id, String description, String shortdescription) {
        this.id = id;
        this.description = description;
        this.shortdescription = shortdescription;
    }

    public String getId() {return this.id;}
    public String getDescription() {return this.description;}
    public String getShortdescription() { return shortdescription; }

    public static NotificationMode getInstanceById(String id) {
        switch (id) {
            case "UNREPEAT": return NotificationMode.UNREPEATABLE;
            case "DAILY": return NotificationMode.DAILY;
            case "WEEK": return NotificationMode.WEEKLY;
            case "MONTH": return NotificationMode.MONTHLY;
            case "ANNUAL": return NotificationMode.ANNUALLY;
            default: return NotificationMode.UNREPEATABLE;
        }
    }
}
