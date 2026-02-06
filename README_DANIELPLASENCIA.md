# Guía de pruebas con Postman – LMS Microservicios

**Autor:** Daniel Plasencia  
**Proyecto:** modulo3-entregable (Trabajo Final – Kafka + RestTemplate)

Esta guía permite probar todos los casos de uso usando **Postman**, sin necesidad de conocer el código. Se asume que ya tienes levantados: **PostgreSQL** (todas las BDs), **Kafka** y los **5 microservicios**.

---

## 1. Puertos y URLs base

| Servicio            | Puerto | URL base                |
|---------------------|--------|-------------------------|
| user-service        | 8081   | http://localhost:8081   |
| course-service      | 8082   | http://localhost:8082   |
| enrollment-service  | 8083   | http://localhost:8083   |
| notification-service| 8084   | http://localhost:8084   |
| payment-service     | 8085   | http://localhost:8085   |

En todas las peticiones usa **Header:** `Content-Type: application/json` cuando envíes body en JSON.

---

## 2. Antes de empezar

- Levantar **Docker Compose** (PostgreSQL + Kafka): desde la raíz del proyecto, `docker-compose up -d` (o solo los servicios que necesites).
- Ejecutar los scripts SQL de cada microservicio en su base de datos (userdb, coursedb, enrollmentdb, paymentdb, notificationdb).
- Iniciar los 5 microservicios (por IDE o Maven) en el orden que prefieras; todos deben estar en ejecución para el flujo completo.

---

## 3. Flujo conceptual: REST, Kafka y BD

A continuación se resume cómo interactúan los servicios a nivel **REST**, **Kafka** y **base de datos**.

### 3.1 Base de datos (persistencia por servicio)

Cada microservicio es dueño de su propia base de datos; no comparten tablas.

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  user-service   │     │ course-service  │     │enrollment-service│
│      ↓          │     │      ↓          │     │       ↓          │
│    userdb       │     │   coursedb      │     │  enrollmentdb   │
│  (tabla users)  │     │ (tabla courses)  │     │(tabla enrollments)│
└─────────────────┘     └─────────────────┘     └─────────────────┘

┌─────────────────┐     ┌─────────────────┐
│ payment-service │     │notification-service│
│      ↓          │     │       ↓          │
│   paymentdb     │     │  notificationdb  │
│ (tabla payments)│     │(tabla notifications)│
└─────────────────┘     └─────────────────┘
```

### 3.2 REST (RestTemplate): llamadas síncronas

Solo **enrollment-service** llama a otros servicios por HTTP (RestTemplate):

- Al **crear una matrícula** (POST /enrollments), enrollment-service:
  1. Llama a **user-service** (GET /users/{id}) para validar que el usuario existe.
  2. Llama a **course-service** (GET /courses/{id}) para validar que el curso existe.
  3. Si ambos responden OK, guarda la matrícula en **enrollmentdb**.

```
   Cliente (Postman)
        │
        │  POST /enrollments { userId, courseId }
        ▼
   ┌─────────────────────┐
   │ enrollment-service  │
   │        │            │
   │        ├── GET ──────────► user-service (userdb)
   │        │   /users/{id}    (validar usuario)
   │        │
   │        ├── GET ──────────► course-service (coursedb)
   │        │   /courses/{id}  (validar curso)
   │        │
   │        └── INSERT ───────► enrollmentdb (enrollments)
   └─────────────────────┘
```

### 3.3 Kafka: eventos asíncronos (publicar / consumir)

Los servicios se comunican por **temas Kafka** sin llamarse por REST. Quién publica y quién consume:

| Topic                   | Quién publica           | Quién consume                          |
|-------------------------|--------------------------|----------------------------------------|
| lms.course.events       | course-service           | notification-service                   |
| lms.enrollment.events  | enrollment-service       | notification-service                   |
| lms.payment.events     | payment-service          | enrollment-service, notification-service |

Flujo conceptual de eventos:

```
  ┌──────────────────┐                    ┌─────────────────────┐
  │ course-service   │── CoursePublished ─►│ lms.course.events   │──► notification-service
  └──────────────────┘   Event            └─────────────────────┘         (escribe notifications)
        │
        │ escribe coursedb

  ┌──────────────────┐                    ┌─────────────────────┐
  │ enrollment-service│── EnrollmentCreated ─►│ lms.enrollment.events │──► notification-service
  │                  │   EnrollmentUpdated  │                     │         (escribe notifications)
  └────────┬─────────┘                    └─────────────────────┘
        │ escribe enrollmentdb                  ▲
        │                                       │ consume
        │                                       │
  ┌─────┴──────────────────┐            ┌─────┴─────────────┐
  │ payment-service        │── Payment  │ lms.payment.events │
  │ (escribe paymentdb)    │   Approved/│                   │
  └────────────────────────┘   Rejected  └─────────┬─────────┘
        │                         Event            │
        │                                          ├──► enrollment-service (actualiza enrollment
        │                                          │    a CONFIRMED/CANCELLED, publica EnrollmentUpdated)
        │                                          │
        │                                          └──► notification-service (escribe notifications)
```

### 3.4 Flujo end-to-end en tres pasos (conceptual)

1. **Matrícula (REST + Kafka + BD)**  
   Cliente → POST /enrollments → enrollment-service valida por REST (user, course) → escribe enrollmentdb → publica EnrollmentCreatedEvent → notification-service consume y escribe notificationdb.

2. **Pago (REST + Kafka + BD)**  
   Cliente → POST /payments → payment-service escribe paymentdb → publica PaymentApprovedEvent o PaymentRejectedEvent → enrollment-service consume, actualiza enrollmentdb (CONFIRMED/CANCELLED) y publica EnrollmentUpdatedEvent → notification-service consume y escribe notificationdb.

3. **Publicar curso (REST + Kafka + BD)**  
   Cliente → POST /courses/{id}/publish → course-service actualiza coursedb → publica CoursePublishedEvent → notification-service consume y escribe notificationdb.

---

## 4. Caso de uso 1: Publicar curso

Objetivo: crear un curso, publicarlo y que notification-service reciba el evento (opcional) y cree una notificación.

### Paso 1.1 – Crear curso

- **Método:** POST  
- **URL:** `http://localhost:8082/courses`  
- **Body (raw, JSON):**

```json
{
  "title": "Introducción a Spring Boot",
  "description": "Curso básico de microservicios"
}
```

- **Resultado esperado:** 201 Created. En la respuesta aparece el curso con `id` (por ejemplo `1`). **Anota el `id` del curso** para el siguiente paso.

### Paso 1.2 – Publicar curso

- **Método:** POST  
- **URL:** `http://localhost:8082/courses/{id}/publish`  
  Sustituye `{id}` por el id del curso creado (ej. `http://localhost:8082/courses/1/publish`).

- **Body:** ninguno (o `{}`).

- **Resultado esperado:** 200 OK. El curso queda con `published: true`. Si notification-service está encendido y consume `lms.course.events`, se creará una notificación de “Curso publicado”.

### Paso 1.3 – Consultar curso (opcional)

- **Método:** GET  
- **URL:** `http://localhost:8082/courses/1`  
- **Resultado esperado:** 200 OK con los datos del curso, incluido `published: true`.

---

## 5. Caso de uso 2: Solicitud de matrícula

Objetivo: crear un usuario y un curso (si no existen), luego crear una matrícula. enrollment-service validará usuario y curso por RestTemplate y publicará EnrollmentCreatedEvent; notification-service creará una notificación.

### Paso 2.1 – Crear usuario (si aún no tienes)

- **Método:** POST  
- **URL:** `http://localhost:8081/users`  
- **Body (raw, JSON):**

```json
{
  "fullName": "Juan Pérez",
  "email": "juan@ejemplo.com",
  "status": "ACTIVE"
}
```

- **Resultado esperado:** 201 Created. Anota el `id` del usuario (ej. `1`).

### Paso 2.2 – Listar usuarios (opcional)

- **Método:** GET  
- **URL:** `http://localhost:8081/users`  
- **Resultado esperado:** 200 OK con lista de usuarios. Elige un `id` para la matrícula.

### Paso 2.3 – Crear curso (si aún no tienes)

- **Método:** POST  
- **URL:** `http://localhost:8082/courses`  
- **Body:** (igual que en **4**, Paso 1.1 – Crear curso). Anota el `id` del curso.

### Paso 2.4 – Listar cursos (opcional)

- **Método:** GET  
- **URL:** `http://localhost:8082/courses`  
- **Resultado esperado:** 200 OK con lista de cursos. Elige un `id` para la matrícula.

### Paso 2.5 – Crear matrícula

- **Método:** POST  
- **URL:** `http://localhost:8083/enrollments`  
- **Body (raw, JSON):**

```json
{
  "userId": 1,
  "courseId": 1
}
```

Sustituye `1` y `1` por los ids reales de un usuario y un curso existentes.

- **Resultado esperado:** 201 Created. La respuesta incluye la matrícula con `id`, `userId`, `courseId`, `status: "PENDING_PAYMENT"`. **Anota el `id` de la matrícula (enrollmentId)** para el Caso 3.

- Si usuario o curso no existen, recibirás **502** (error de user-service o course-service).

### Paso 2.6 – Consultar matrícula (opcional)

- **Método:** GET  
- **URL:** `http://localhost:8083/enrollments/1`  
- **Resultado esperado:** 200 OK con la matrícula en estado `PENDING_PAYMENT`.

### Paso 2.7 – Listar matrículas por usuario (opcional)

- **Método:** GET  
- **URL:** `http://localhost:8083/enrollments?userId=1`  
- **Resultado esperado:** 200 OK con un array de matrículas del usuario 1.

---

## 6. Caso de uso 3: Pago (aprobado y rechazado)

Objetivo: registrar un pago asociado a una matrícula. payment-service publica el evento; enrollment-service actualiza la matrícula a CONFIRMED o CANCELLED; notification-service crea la notificación correspondiente.

### Paso 3.1 – Pago aprobado

- **Método:** POST  
- **URL:** `http://localhost:8085/payments`  
- **Body (raw, JSON):**

```json
{
  "enrollmentId": 1,
  "amount": 99.50
}
```

Sustituye `1` por el **enrollmentId** de una matrícula en estado `PENDING_PAYMENT` (obtenido en el Caso 2).

- **Resultado esperado:** 201 Created. Respuesta con el pago creado y `status: "APPROVED"` (por defecto).

- **Comprobar matrícula actualizada:** GET `http://localhost:8083/enrollments/1` → la matrícula debe tener `status: "CONFIRMED"`.

### Paso 3.2 – Pago rechazado

Primero crea **otra matrícula** (repite Pasos 2.5 con otro usuario/curso o los mismos) para tener una matrícula aún en `PENDING_PAYMENT`. Luego:

- **Método:** POST  
- **URL:** `http://localhost:8085/payments`  
- **Body (raw, JSON):**

```json
{
  "enrollmentId": 2,
  "amount": 50.00,
  "status": "REJECTED"
}
```

Sustituye `2` por el id de esa segunda matrícula.

- **Resultado esperado:** 201 Created. El pago queda con `status: "REJECTED"`.

- **Comprobar matrícula:** GET `http://localhost:8083/enrollments/2` → la matrícula debe tener `status: "CANCELLED"`.

### Paso 3.3 – Consultar pago (opcional)

- **Método:** GET  
- **URL:** `http://localhost:8085/payments/1`  
- **Resultado esperado:** 200 OK con el pago (id, enrollmentId, amount, status, etc.).

---

## 7. Notificaciones

El notification-service consume eventos de Kafka y guarda notificaciones en la base de datos. No es obligatorio tener un endpoint para listarlas; si existe, puedes usarlo. En cualquier caso puedes comprobar los resultados en la base **notificationdb**, tabla **notifications**.

### Consultar una notificación por id (si el servicio lo expone)

- **Método:** GET  
- **URL:** `http://localhost:8084/notifications/1`  
- **Resultado esperado:** 200 OK con la notificación (id, user_id, message, sent, created_at).

### Health

- **Método:** GET  
- **URL:** `http://localhost:8084/notifications/health`  
- **Resultado esperado:** 200 OK.

---

## 8. Resumen del flujo completo (orden sugerido)

1. **User:** POST `/users` → anotar `userId`.  
2. **Course:** POST `/courses` → anotar `courseId`.  
3. **Opcional – Publicar curso:** POST `/courses/{courseId}/publish`.  
4. **Enrollment:** POST `/enrollments` con `userId` y `courseId` → anotar `enrollmentId`.  
5. **Payment aprobado:** POST `/payments` con `enrollmentId` y `amount`.  
6. **Comprobar:** GET `/enrollments/{enrollmentId}` → `status: "CONFIRMED"`.  
7. **Payment rechazado (otra matrícula):** crear otra matrícula, luego POST `/payments` con `enrollmentId` de esa matrícula y `"status": "REJECTED"`.  
8. **Comprobar:** GET `/enrollments/{enrollmentId}` → `status: "CANCELLED"`.  
9. **Notificaciones:** revisar tabla `notifications` en notificationdb o GET `/notifications/{id}` si está disponible.

---

## 9. Health de todos los servicios

Puedes verificar que cada servicio esté arriba con:

| Servicio            | URL de health                          |
|---------------------|----------------------------------------|
| user-service        | GET http://localhost:8081/users/health  |
| course-service      | GET http://localhost:8082/courses/health |
| enrollment-service  | GET http://localhost:8083/enrollments/health |
| notification-service| GET http://localhost:8084/notifications/health |
| payment-service     | GET http://localhost:8085/payments/health |

Todos deben responder 200 OK con un mensaje de tipo “Service running”.

---

## 10. Errores frecuentes

- **502 al crear matrícula:** El `userId` o `courseId` no existe en user-service o course-service. Usa ids que devuelvan GET `/users` y GET `/courses`.  
- **Matrícula no pasa a CONFIRMED/CANCELLED:** Asegúrate de que Kafka esté levantado y de que enrollment-service y payment-service estén en ejecución cuando hagas el POST a `/payments`.  
- **Puerto en uso:** Cada servicio tiene un puerto fijo (8081–8085). Si otro proceso usa ese puerto, detén el proceso o cambia el puerto en el `application.properties` del servicio correspondiente.

Con esta guía puedes probar todos los casos de uso del trabajo final usando solo Postman y los servicios en marcha.
