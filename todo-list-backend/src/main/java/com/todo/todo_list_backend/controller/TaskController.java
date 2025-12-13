package com.todo.todo_list_backend.controller;

import com.todo.todo_list_backend.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TaskController {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Obtener tareas por usuario: GET /api/tasks?userId=1
    @GetMapping
    public List<Task> getTasks(@RequestParam Long userId) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks.values()) {
            if (Objects.equals(t.getUserId(), userId)) {
                result.add(t);
            }
        }
        return result;
    }

    // Crear tarea: POST /api/tasks
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task request) {
        if (request.getUserId() == null || request.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }
        Long id = idGenerator.getAndIncrement();
        Task task = new Task(id, request.getUserId(), request.getDescription(), false);
        tasks.put(id, task);
        return ResponseEntity.ok(task);
    }

    // Actualizar tarea: PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task request) {
        Task existing = tasks.get(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }
        existing.setCompleted(request.isCompleted());
        return ResponseEntity.ok(existing);
    }

    // Eliminar tarea: DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task removed = tasks.remove(id);
        if (removed == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

