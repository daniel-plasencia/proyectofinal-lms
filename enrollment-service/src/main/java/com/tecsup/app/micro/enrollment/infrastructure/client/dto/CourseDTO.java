package com.tecsup.app.micro.enrollment.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de course-service (GET /courses/{id}).
 * Campos alineados con CourseResponse del course-service en modulo3-entregable.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean published;
    private LocalDateTime createdAt;
}
