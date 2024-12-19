package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import java.sql.Timestamp;

public class Task {
    private int id;
    private String tittle;
    private String description;
    private String dueDate;
    private PriorityLevel priorityLevel;
    private CompletionStatus completionStatus;
    private Boolean reminder;
    public Task(int id, String tittle, String description, String dueDate, PriorityLevel priorityLevel, CompletionStatus completionStatus,Boolean reminder) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.dueDate = dueDate;
        this.priorityLevel = priorityLevel;
        this.completionStatus = completionStatus;
        this.reminder=reminder;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }

    public Boolean getReminder() {
        return reminder;
    }

    public void setReminder(Boolean reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "tittle:" + tittle + '\n' +
                "description:" + description + '\n' +
                "completionStatus:" + completionStatus + '\n' +
                "dueDate:" + dueDate + '\n' +
                "priorityLevel:" + priorityLevel+ '\n' +
                "reminder:" + reminder;
    }
}
