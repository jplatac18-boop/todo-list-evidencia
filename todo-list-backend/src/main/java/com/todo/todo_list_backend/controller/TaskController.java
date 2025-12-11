package com.todo.todo_list_backend.controller;

import com.todo.todo_list_backend.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final Map<Long, Task> tasks = new LinkedHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @GetMapping
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        Long id = idGenerator.getAndIncrement();
        task.setId(id);
        task.setCompleted(false);
        tasks.put(id, task);
        return task;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updated) {
        Task existing = tasks.get(id);
        if (existing == null) {
            return null;
        }
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());
        return existing;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasks.remove(id);
    }

    @GetMapping("/stats")
    public Map<String, Integer> getStats() {
        int total = tasks.size();
        int completed = (int) tasks.values().stream().filter(Task::isCompleted).count();
        int pending = total - completed;
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("completed", completed);
        stats.put("pending", pending);
        return stats;
    }
}
