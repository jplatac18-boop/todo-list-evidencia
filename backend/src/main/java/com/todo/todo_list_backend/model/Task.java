package com.todo.todo_list_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Task", description = "Modelo de tarea asociada a un usuario")
public class Task {

    @Schema(description = "Identificador único de la tarea", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador del usuario dueño de la tarea", example = "10")
    private Long userId;

    @Schema(description = "Descripción corta de la tarea", example = "Estudiar para la evaluación")
    private String description;

    @Schema(description = "Indica si la tarea está completada", example = "false")
    private boolean completed;

    public Task() {}

    public Task(Long id, Long userId, String description, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
