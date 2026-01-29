# User Service - Clean Architecture

Microservicio de gestión de usuarios implementado con **Clean Architecture** y Spring Boot.

## 🏗️ Arquitectura

Este proyecto sigue los principios de Clean Architecture, organizando el código en capas bien definidas:

```
src/main/java/com/tecsup/app/micro/user/
├── domain/                    # Capa de Dominio (Core)
│   ├── model/                 # Entidades de negocio
│   │   └── User.java
│   ├── repository/            # Puertos (Interfaces)
│   │   └── UserRepository.java
│   └── exception/             # Excepciones de dominio
│       ├── UserNotFoundException.java
│       ├── DuplicateEmailException.java
│       └── InvalidUserDataException.java
│
├── application/               # Capa de Aplicación
│   ├── usecase/              # Casos de uso
│   │   ├── GetAllUsersUseCase.java
│   │   ├── GetUserByIdUseCase.java
│   │   ├── CreateUserUseCase.java
│   │   ├── UpdateUserUseCase.java
│   │   └── DeleteUserUseCase.java
│   └── service/              # Servicios de aplicación
│       └── UserApplicationService.java
│
├── infrastructure/            # Capa de Infraestructura
│   └── persistence/
│       ├── entity/           # Entidades JPA
│       │   └── UserEntity.java
│       └── repository/       # Adaptadores de repositorio
│           ├── JpaUserRepository.java
│           └── UserRepositoryImpl.java
│
└── presentation/             # Capa de Presentación
    ├── controller/           # Controladores REST
    │   ├── UserController.java
    │   └── GlobalExceptionHandler.java
    ├── dto/                  # DTOs de API
    │   ├── CreateUserRequest.java
    │   ├── UpdateUserRequest.java
    │   └── UserResponse.java
    └── mapper/               # Mappers DTO-Domain
        └── UserDtoMapper.java
```

## 📋 Principios de Clean Architecture

### 1. **Domain (Dominio)**
- **Responsabilidad**: Contiene la lógica de negocio central
- **Independencia**: No depende de ninguna capa externa
- **Contenido**: 
  - Entidades de negocio (`User`)
  - Interfaces de repositorio (puertos)
  - Excepciones de dominio
  - Reglas de negocio

### 2. **Application (Aplicación)**
- **Responsabilidad**: Orquesta la lógica de negocio
- **Contenido**:
  - Casos de uso (Use Cases)
  - Servicios de aplicación
  - Validaciones de negocio

### 3. **Infrastructure (Infraestructura)**
- **Responsabilidad**: Implementaciones técnicas
- **Contenido**:
  - Adaptadores de persistencia
  - Implementaciones de repositorios
  - Configuraciones de frameworks

### 4. **Presentation (Presentación)**
- **Responsabilidad**: Interfaz con el exterior
- **Contenido**:
  - Controladores REST
  - DTOs de entrada/salida
  - Mappers
  - Manejadores de excepciones

## 🚀 Características

- ✅ Clean Architecture
- ✅ Separación de responsabilidades
- ✅ Independencia de frameworks
- ✅ Testeable
- ✅ Validación de datos
- ✅ Manejo de excepciones centralizado
- ✅ DTOs tipados
- ✅ Logging estructurado
- ✅ Sin Flyway (gestión de esquema con JPA)

## 🔧 Tecnologías

- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

## 📦 Requisitos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+

## ⚙️ Configuración

### Base de Datos

El proyecto usa JPA con `ddl-auto=update` para gestión automática del esquema.

**Opción 1: Dejar que JPA cree las tablas** (Recomendado para desarrollo)
```properties
spring.jpa.hibernate.ddl-auto=update
```

**Opción 2: Crear la base de datos manualmente**

Si prefieres crear la base de datos manualmente, ejecuta los scripts SQL:

```bash
# Conectar a PostgreSQL
psql -U postgres

# Crear base de datos
CREATE DATABASE userdb;

# Conectar a la base de datos
\c userdb

# Ejecutar script de inicialización
\i database/init.sql

# (Opcional) Insertar datos de ejemplo
\i database/sample_data.sql
```

### Configuración de aplicación

Edita `src/main/resources/application.properties`:

```properties
# Base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update
```

## 🏃 Ejecución

### Compilar el proyecto
```bash
mvn clean install
```

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8081`

## 🌐 API Endpoints

### Health Check
```http
GET /api/users/health
```

### Obtener todos los usuarios
```http
GET /api/users
```

### Obtener usuario por ID
```http
GET /api/users/{id}
```

### Crear usuario
```http
POST /api/users
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan.perez@example.com",
  "phone": "+51-999-123-456",
  "address": "Av. Arequipa 1234, Lima"
}
```

### Actualizar usuario
```http
PUT /api/users/{id}
Content-Type: application/json

{
  "name": "Juan Pérez Actualizado",
  "email": "juan.perez@example.com",
  "phone": "+51-999-123-456",
  "address": "Nueva dirección"
}
```

### Eliminar usuario
```http
DELETE /api/users/{id}
```

## 📝 Respuestas de Error

El servicio devuelve respuestas de error estructuradas:

### Usuario no encontrado (404)
```json
{
  "status": 404,
  "message": "User not found with id: 1",
  "timestamp": "2024-01-28T10:30:00"
}
```

### Email duplicado (409)
```json
{
  "status": 409,
  "message": "Email already exists: user@example.com",
  "timestamp": "2024-01-28T10:30:00"
}
```

### Validación fallida (400)
```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2024-01-28T10:30:00",
  "errors": {
    "email": "Email must be valid",
    "name": "Name is required"
  }
}
```

## 🧪 Testing

```bash
mvn test
```

## 📚 Diferencias con el proyecto original

### Sin Flyway
- **Antes**: Migraciones con Flyway
- **Ahora**: JPA con `ddl-auto=update` para desarrollo
- **Ventaja**: Simplicidad, menos dependencias
- **Scripts SQL**: Disponibles en `/database` para uso manual si es necesario

### Arquitectura
- **Antes**: Arquitectura por capas tradicional
- **Ahora**: Clean Architecture con separación clara de responsabilidades
- **Ventaja**: Mayor testabilidad, independencia de frameworks, código más mantenible

### Organización
- **Antes**: Código organizado por tipo técnico
- **Ahora**: Código organizado por capas arquitectónicas
- **Ventaja**: Mejor separación de conceptos, más escalable

## 🎯 Ventajas de Clean Architecture

1. **Independencia de Frameworks**: El dominio no depende de Spring
2. **Testeable**: Fácil de escribir tests unitarios
3. **Independencia de UI**: Puedes cambiar la interfaz sin afectar el dominio
4. **Independencia de BD**: Puedes cambiar la base de datos sin afectar el dominio
5. **Reglas de negocio centralizadas**: Toda la lógica de negocio en un solo lugar

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👥 Autor

Desarrollado como ejemplo de Clean Architecture para el curso de Microservicios - Tecsup.
