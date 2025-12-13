package com.todo.todo_list_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User", description = "Modelo de usuario (almacenado en memoria para la demo)")
public class User {

    @Schema(
            description = "Identificador único del usuario",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id; // por ahora simulado en memoria

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String name;

    @Schema(description = "Correo del usuario", example = "juan@email.com")
    private String email;

    @Schema(
            description = "Contraseña del usuario (solo se envía en requests)",
            example = "123456",
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    private String password;

    public User() {}

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
