package com.tecsup.app.micro.notification.infrastructure.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para deserializar eventos de lms.enrollment.events (EnrollmentCreatedEvent / EnrollmentUpdatedEvent).
 * ignoreUnknown = true para ignorar @class y otros campos que envíe el producer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollmentEventDto {

    private String eventType;
    private Long userId;
    private Long enrollmentId;
    private Long courseId;
    private String status;
}
