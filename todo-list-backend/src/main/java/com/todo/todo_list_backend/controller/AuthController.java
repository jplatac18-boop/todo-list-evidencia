package com.todo.todo_list_backend.controller;

import com.todo.todo_list_backend.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5500") // ajusta puerto del front
public class AuthController {

    private final Map<String, User> usersByEmail = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        if (usersByEmail.containsKey(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "El correo ya está registrado"));
        }

        Long id = idGenerator.getAndIncrement();
        User user = new User(id, request.getName(), request.getEmail(), request.getPassword());
        usersByEmail.put(user.getEmail(), user);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("token", UUID.randomUUID().toString());

        return ResponseEntity.ok(response);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        User user = usersByEmail.get(email);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Credenciales inválidas"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("token", UUID.randomUUID().toString());

        return ResponseEntity.ok(response);
    }
}
