package com.todo.todo_list_backend.controller;

import com.todo.todo_list_backend.model.Task;
import com.todo.todo_list_backend.model.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Tareas", description = "Servicios CRUD de tareas (filtradas por userId)")
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // GET /api/tasks?userId=1
    @Operation(
            summary = "Listar tareas por usuario",
            description = "Devuelve todas las tareas asociadas al userId enviado como query param."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tareas devuelta correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro userId faltante o inválido")
    })
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestParam Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Task> result = taskRepository.findByUserId(userId);
        return ResponseEntity.ok(result);
    }

    // POST /api/tasks
    @Operation(
            summary = "Crear tarea",
            description = "Crea una tarea nueva asociada al userId del body."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Body inválido (userId o description faltante)")
    })
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task request) {
        if (request.getUserId() == null || request.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }
        // startDate, endDate, longDescription y starred ya vienen del body
        Task saved = taskRepository.save(request);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/tasks/{id}
    @Operation(
            summary = "Actualizar tarea",
            description = "Actualiza los campos de una tarea existente por su id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task request) {
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Task existing = optional.get();

        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getLongDescription() != null) existing.setLongDescription(request.getLongDescription());
        if (request.getStartDate() != null) existing.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) existing.setEndDate(request.getEndDate());
        existing.setCompleted(request.isCompleted());
        existing.setStarred(request.isStarred());

        Task updated = taskRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/tasks/{id}
    @Operation(
            summary = "Eliminar tarea",
            description = "Elimina una tarea por su id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarea eliminada correctamente (sin contenido)"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
