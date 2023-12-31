package com.ufcg.taskgenerator;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Task {

    String id;
    String title;
    String description;
    LocalDate expirationDate;
    PRIORITY priority;

    public Task(){};
    public Task(String id, String title, String description, LocalDate expirationDate, PRIORITY priority)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
        this.priority = priority;
    }
    public Task(String title, String description, LocalDate expirationDate, PRIORITY priority)
    {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public PRIORITY getPriority() {
        return priority;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPriority(PRIORITY priority) {
        this.priority = priority;
    }
}
