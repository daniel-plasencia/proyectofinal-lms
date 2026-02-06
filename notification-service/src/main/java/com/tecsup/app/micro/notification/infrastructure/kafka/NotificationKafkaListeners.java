package com.tecsup.app.micro.notification.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.app.micro.notification.application.usecase.CreateNotificationUseCase;
import com.tecsup.app.micro.notification.infrastructure.config.KafkaConfig;
import com.tecsup.app.micro.notification.infrastructure.kafka.dto.CoursePublishedEventDto;
import com.tecsup.app.micro.notification.infrastructure.kafka.dto.EnrollmentEventDto;
import com.tecsup.app.micro.notification.infrastructure.kafka.dto.PaymentEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationKafkaListeners {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaConfig.PAYMENT_EVENTS_TOPIC, groupId = "notification-service-group")
    public void onPaymentEvent(String payload) {
        try {
            PaymentEventDto event = objectMapper.readValue(payload, PaymentEventDto.class);
            log.info("Received payment event: paymentId={}, enrollmentId={}, status={}",
                    event.getPaymentId(), event.getEnrollmentId(), event.getStatus());
            long userId = 0L;
            String message = "APPROVED".equalsIgnoreCase(event.getStatus())
                    ? "Pago aprobado para matrícula " + event.getEnrollmentId()
                    : "Pago rechazado para matrícula " + event.getEnrollmentId();
            createNotificationUseCase.execute(userId, message);
        } catch (Exception e) {
            log.error("Error parsing payment event payload: {}", payload, e);
        }
    }

    @KafkaListener(topics = KafkaConfig.COURSE_EVENTS_TOPIC, groupId = "notification-service-group")
    public void onCoursePublishedEvent(String payload) {
        try {
            CoursePublishedEventDto event = objectMapper.readValue(payload, CoursePublishedEventDto.class);
            log.info("Received course event: courseId={}, title={}", event.getCourseId(), event.getTitle());
            String message = "Curso publicado: " + (event.getTitle() != null ? event.getTitle() : event.getCourseId());
            createNotificationUseCase.execute(0L, message);
        } catch (Exception e) {
            log.error("Error parsing course event payload: {}", payload, e);
        }
    }

    @KafkaListener(topics = KafkaConfig.ENROLLMENT_EVENTS_TOPIC, groupId = "notification-service-group")
    public void onEnrollmentEvent(String payload) {
        try {
            EnrollmentEventDto event = objectMapper.readValue(payload, EnrollmentEventDto.class);
            log.info("Received enrollment event: eventType={}, userId={}, enrollmentId={}",
                    event.getEventType(), event.getUserId(), event.getEnrollmentId());
            long userId = event.getUserId() != null ? event.getUserId() : 0L;
            String message = "EnrollmentCreatedEvent".equals(event.getEventType())
                    ? "Matrícula creada (enrollment " + event.getEnrollmentId() + ")"
                    : "Matrícula actualizada (enrollment " + event.getEnrollmentId() + ") - " + event.getStatus();
            createNotificationUseCase.execute(userId, message);
        } catch (Exception e) {
            log.error("Error parsing enrollment event payload: {}", payload, e);
        }
    }
}
