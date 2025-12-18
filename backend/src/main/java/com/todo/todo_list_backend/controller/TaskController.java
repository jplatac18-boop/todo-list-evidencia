package com.todo.todo_list_backend.controller;

import com.todo.todo_list_backend.model.Task;
import com.todo.todo_list_backend.model.TaskRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tareas", description = "Servicios para gestionar tareas de un usuario")
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5500")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<?> getTasksByUser(@RequestParam String userId) {
        if (userId == null || userId.isBlank()) {
            return ResponseEntity.badRequest().body("userId es obligatorio");
        }
        List<Task> tasks = taskRepository.findByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        if (task.getUserId() == null || task.getUserId().isBlank()) {
            return ResponseEntity.badRequest().body("userId es obligatorio");
        }
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            return ResponseEntity.badRequest().body("description es obligatorio");
        }
        task.setId(null);
        Task saved = taskRepository.save(task);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskRepository.findById(id)
                .<ResponseEntity<?>>map(existing -> {
                    if (task.getDescription() != null) {
                        existing.setDescription(task.getDescription());
                    }
                    if (task.getLongDescription() != null) {
                        existing.setLongDescription(task.getLongDescription());
                    }
                    if (task.getStartDate() != null) {
                        existing.setStartDate(task.getStartDate());
                    }
                    if (task.getEndDate() != null) {
                        existing.setEndDate(task.getEndDate());
                    }
                    existing.setCompleted(task.isCompleted());
                    existing.setStarred(task.isStarred());
                    Task updated = taskRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
