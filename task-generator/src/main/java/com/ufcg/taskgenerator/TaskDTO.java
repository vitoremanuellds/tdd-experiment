package com.ufcg.taskgenerator;

import java.util.Date;

public class TaskDTO {

    private String title;
    private String description;
    private String date;
    private PRIORITY priority;

    public TaskDTO(String title, String description, String date, PRIORITY priority)
    {
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public PRIORITY getPriority() {
        return priority;
    }

    public void setPriority(PRIORITY priority) {
        this.priority = priority;
    }
}
