package com.todo.todo_list_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Schema(name = "Task", description = "Modelo de tarea asociada a un usuario")
public class Task {

    @Id
    @Schema(description = "Identificador único de la tarea", example = "664f1a2b9f1c3a0a2c123456",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Identificador del usuario dueño de la tarea", example = "10")
    private Long userId;

    @Schema(description = "Descripción corta de la tarea", example = "Estudiar para la evaluación")
    private String description;

    @Schema(description = "Descripción larga de la tarea", example = "Estudiar los temas 1 al 5")
    private String longDescription;

    @Schema(description = "Fecha de inicio (YYYY-MM-DD)", example = "2025-12-18")
    private String startDate;

    @Schema(description = "Fecha de fin (YYYY-MM-DD)", example = "2025-12-20")
    private String endDate;

    @Schema(description = "Indica si la tarea está completada", example = "false")
    private boolean completed;

    @Schema(description = "Indica si la tarea está marcada como destacada", example = "true")
    private boolean starred;

    public Task() {
    }

    public Task(Long userId, String description) {
        this.userId = userId;
        this.description = description;
    }

    // getters y setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLongDescription() { return longDescription; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean isStarred() { return starred; }
    public void setStarred(boolean starred) { this.starred = starred; }
}
