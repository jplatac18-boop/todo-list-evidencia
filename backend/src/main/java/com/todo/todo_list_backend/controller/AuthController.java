package com.todo.todo_list_backend.controller;

import com.todo.todo_list_backend.model.UserRepository;
import com.todo.todo_list_backend.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Tag(name = "Autenticación", description = "Servicios de registro e inicio de sesión")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5500") // ajusta puerto del front
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "Registro de usuario",
            description = "Registra un usuario en MongoDB si el correo no existe y retorna un token de sesión (demo)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "El correo ya está registrado o el body es inválido")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request == null || request.email == null || request.email.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("El email es obligatorio"));
        }

        Optional<User> existing = userRepository.findByEmail(request.email);
        if (existing.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("El correo ya está registrado"));
        }

        User user = new User(request.name, request.email, request.password);
        User saved = userRepository.save(user);

        AuthResponse response = new AuthResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                UUID.randomUUID().toString()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Login de usuario",
            description = "Valida credenciales contra usuarios almacenados en MongoDB y retorna un token (demo)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login correcto"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String email = (request == null) ? null : request.email;
        String password = (request == null) ? null : request.password;

        Optional<User> optUser = (email == null) ? Optional.empty() : userRepository.findByEmail(email);
        if (optUser.isEmpty() || password == null || !optUser.get().getPassword().equals(password)) {
            return ResponseEntity
                    .status(401)
                    .body(new ErrorResponse("Credenciales inválidas"));
        }

        User user = optUser.get();

        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                UUID.randomUUID().toString()
        );

        return ResponseEntity.ok(response);
    }

    // ===== DTOs para documentar Request/Response en Swagger =====

    @Schema(name = "RegisterRequest", description = "Datos para registrar un usuario")
    public static class RegisterRequest {
        @Schema(example = "Juan Pérez")
        public String name;

        @Schema(example = "juan@email.com")
        public String email;

        @Schema(example = "123456")
        public String password;
    }

    @Schema(name = "LoginRequest", description = "Datos para iniciar sesión")
    public static class LoginRequest {
        @Schema(example = "juan@email.com")
        public String email;

        @Schema(example = "123456")
        public String password;
    }

    @Schema(name = "AuthResponse", description = "Respuesta de autenticación")
    public static class AuthResponse {
        public String id;
        public String name;
        public String email;
        public String token;

        public AuthResponse(String id, String name, String email, String token) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.token = token;
        }
    }

    @Schema(name = "ErrorResponse", description = "Respuesta de error estándar")
    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}
