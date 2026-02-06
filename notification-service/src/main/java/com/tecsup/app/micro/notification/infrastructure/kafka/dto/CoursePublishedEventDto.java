package com.tecsup.app.micro.notification.infrastructure.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para deserializar eventos de lms.course.events (CoursePublishedEvent).
 * ignoreUnknown = true para ignorar @class y otros campos que envíe el producer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoursePublishedEventDto {

    private String eventId;
    private String eventType;
    private LocalDateTime occurredOn;
    private String courseId;
    private String title;
    private String description;
}
