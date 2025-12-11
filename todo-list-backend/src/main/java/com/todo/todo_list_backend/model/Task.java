package com.todo.todo_list_backend.model;

public class Task {

    private Long id;
    private String description;
    private Boolean completed;

    public Task() {
    }

    public Task(Long id, String description, Boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 200) {
            this.description = description.substring(0, 200);
        } else {
            this.description = description;
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
