package com.tecsup.app.micro.enrollment.infrastructure.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Evento publicado al crear matrícula (TRABAJO_FINAL: EnrollmentCreatedEvent).
 * Formato alineado con EnrollmentEventDto en notification-service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentCreatedEvent {

    private String eventType = "EnrollmentCreatedEvent";
    private Long userId;
    private Long enrollmentId;
    private Long courseId;
    private String status;
}
