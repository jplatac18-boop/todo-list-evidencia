package com.todo.todo_list_backend.controller;

import com.todo.todo_list_backend.model.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Tag(name = "Tareas", description = "Servicios CRUD de tareas (filtradas por userId)")
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TaskController {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Obtener tareas por usuario: GET /api/tasks?userId=1
    @Operation(
            summary = "Listar tareas por usuario",
            description = "Devuelve todas las tareas asociadas al userId enviado como query param."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tareas devuelta correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro userId faltante o inválido")
    })
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
    @Operation(
            summary = "Crear tarea",
            description = "Crea una tarea nueva en memoria asociada al userId del body."
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
        Long id = idGenerator.getAndIncrement();
        Task task = new Task(id, request.getUserId(), request.getDescription(), false);
        tasks.put(id, task);
        return ResponseEntity.ok(task);
    }

    // Actualizar tarea: PUT /api/tasks/{id}
    @Operation(
            summary = "Actualizar tarea",
            description = "Actualiza description y completed de una tarea existente por su id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
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
    @Operation(
            summary = "Eliminar tarea",
            description = "Elimina una tarea por su id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarea eliminada correctamente (sin contenido)"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task removed = tasks.remove(id);
        if (removed == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

