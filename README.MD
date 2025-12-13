# To-Do List (Gestor de tareas)

Aplicación web para gestionar tareas (crear, listar, buscar, filtrar, completar, eliminar, destacar) con autenticación (registro e inicio de sesión). 

## Estructura del proyecto
- `frontend/`: interfaz web (HTML/CSS/JS).  
- `backend/`: API REST (Spring Boot + Maven). 

## Requisitos
- Java (JDK) instalado.
- Maven instalado.
- Navegador web.
- (Recomendado) VS Code con extensión **Live Server** para servir el frontend.

## Ejecutar el Backend (Spring Boot)
1. Abrir una terminal en:
   `C:\Users\USUARIO\Desktop\todo-list-evidencia\backend` 
2. Ejecutar:
   `mvn spring-boot:run` 
3. El backend quedará disponible en:
   `http://localhost:8080` 

## Ejecutar el Frontend
1. Abrir la carpeta:
   `C:\Users\USUARIO\Desktop\todo-list-evidencia\frontend`
2. Levantar un servidor estático (por ejemplo con Live Server) y abrir:
   - `frontend/auth.html` (registro/login) 
   - `frontend/index.html` (gestor de tareas)

> El usuario autenticado se guarda en `localStorage` con la clave `currentUser`. 

## Endpoints consumidos por el Frontend
### Autenticación
Base: `http://localhost:8080/api/auth` 
- `POST /register`
- `POST /login`

### Tareas
Base: `http://localhost:8080/api/tasks` 
- `GET /api/tasks?userId={id}` 
- `POST /api/tasks` 
- `PUT /api/tasks/{id}` 
- `DELETE /api/tasks/{id}` 

## Pruebas unitarias (Frontend)
Se incluyen pruebas unitarias de la lógica de `ListaDeTareas` (agregar, eliminar, buscar, cambiar estado, destacar y estadísticas).  
1. Abrir en el navegador:
   `frontend/tests/run-tests.html`
2. Verificar que el resultado muestre OK y tomar captura como evidencia.
