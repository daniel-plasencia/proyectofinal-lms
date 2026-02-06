package com.tecsup.app.micro.enrollment.infrastructure.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Evento publicado al actualizar matrícula (TRABAJO_FINAL: EnrollmentUpdatedEvent).
 * Formato alineado con EnrollmentEventDto en notification-service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentUpdatedEvent {

    private String eventType = "EnrollmentUpdatedEvent";
    private Long userId;
    private Long enrollmentId;
    private Long courseId;
    private String status;
}
