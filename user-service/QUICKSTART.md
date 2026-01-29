# 🚀 Guía de Inicio Rápido

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- Docker y Docker Compose (opcional, para PostgreSQL)

## Opción 1: Usando Docker para PostgreSQL (Recomendado)

### 1. Iniciar PostgreSQL con Docker

```bash
# Desde el directorio raíz del proyecto
docker-compose up -d
```

Esto iniciará un contenedor de PostgreSQL con:
- Base de datos: `userdb`
- Usuario: `postgres`
- Password: `postgres`
- Puerto: `5432`

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

o

```bash
java -jar target/user-service-0.0.1-SNAPSHOT.jar
```

### 4. Verificar que funciona

Abre tu navegador y visita:
```
http://localhost:8081/api/users/health
```

Deberías ver: `User Service running with Clean Architecture!`

## Opción 2: Usando PostgreSQL Instalado Localmente

### 1. Crear la base de datos

```bash
# Conectar a PostgreSQL
psql -U postgres

# Ejecutar en psql:
CREATE DATABASE userdb;
\q
```

### 2. (Opcional) Ejecutar scripts de inicialización

Si prefieres crear las tablas manualmente en lugar de dejar que JPA lo haga:

```bash
psql -U postgres -d userdb -f database/init.sql
psql -U postgres -d userdb -f database/sample_data.sql
```

### 3. Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

## 🧪 Probar la API

### Usando curl

#### Obtener todos los usuarios
```bash
curl http://localhost:8081/api/users
```

#### Crear un usuario
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "phone": "+51-999-999-999",
    "address": "Test Address"
  }'
```

#### Obtener un usuario por ID
```bash
curl http://localhost:8081/api/users/1
```

#### Actualizar un usuario
```bash
curl -X PUT http://localhost:8081/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated User",
    "email": "updated@example.com",
    "phone": "+51-888-888-888",
    "address": "Updated Address"
  }'
```

#### Eliminar un usuario
```bash
curl -X DELETE http://localhost:8081/api/users/1
```

### Usando Postman

1. Importa la colección de Postman (si tienes una)
2. O crea manualmente las requests con los endpoints listados arriba

## 🐛 Solución de Problemas

### Error: "Connection refused" al conectar a PostgreSQL

**Solución:**
- Si usas Docker: Verifica que el contenedor está corriendo con `docker ps`
- Si usas PostgreSQL local: Verifica que el servicio está corriendo

### Error: "Port 8081 already in use"

**Solución:**
- Cambia el puerto en `application.properties`:
  ```properties
  server.port=8082
  ```

### Error: "Table 'users' doesn't exist"

**Solución:**
- Verifica que `spring.jpa.hibernate.ddl-auto=update` está configurado
- O ejecuta manualmente el script `database/init.sql`

## 🛑 Detener la Aplicación

### Detener Spring Boot
Presiona `Ctrl+C` en la terminal donde está corriendo

### Detener Docker
```bash
docker-compose down
```

## 📝 Siguientes Pasos

1. Explora la estructura del código en el README principal
2. Lee sobre la arquitectura en ARCHITECTURE.md
3. Añade tus propias funcionalidades siguiendo el patrón establecido
4. Escribe tests para tus casos de uso

## 🎯 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/users | Obtener todos los usuarios |
| GET | /api/users/{id} | Obtener usuario por ID |
| POST | /api/users | Crear nuevo usuario |
| PUT | /api/users/{id} | Actualizar usuario |
| DELETE | /api/users/{id} | Eliminar usuario |
| GET | /api/users/health | Health check |

## 💡 Consejos

- Usa un cliente REST como Postman o Insomnia para probar la API
- Revisa los logs de la aplicación para ver las queries SQL que se ejecutan
- El proyecto usa validación automática, así que asegúrate de enviar datos válidos
- Todos los endpoints devuelven JSON
