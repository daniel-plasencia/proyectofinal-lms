package com.tecsup.app.micro.enrollment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Modelo de dominio Enrollment (TRABAJO_FINAL.md).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    private Long id;
    private Long userId;
    private Long courseId;
    private String status; // PENDING_PAYMENT, CONFIRMED, CANCELLED
    private LocalDateTime createdAt;
}
