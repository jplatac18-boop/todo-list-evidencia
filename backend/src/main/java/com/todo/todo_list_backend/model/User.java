package com.todo.todo_list_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Schema(name = "User", description = "Modelo de usuario")
public class User {

    @Id
    @Schema(
            description = "Identificador único del usuario",
            example = "6650ab1f4f2c1b0d9c123456",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String id;

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

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // getters y setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
