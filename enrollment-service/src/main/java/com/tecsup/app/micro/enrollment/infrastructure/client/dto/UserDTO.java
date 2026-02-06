package com.tecsup.app.micro.enrollment.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de user-service (GET /users/{id}).
 * Campos alineados con UserResponse del user-service en modulo3-entregable.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String status;
    private LocalDateTime createdAt;
}
