package com.tecsup.app.micro.enrollment.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.app.micro.enrollment.application.usecase.UpdateEnrollmentStatusUseCase;
import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.infrastructure.config.KafkaConfig;
import com.tecsup.app.micro.enrollment.infrastructure.kafka.dto.PaymentEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consume lms.payment.events: actualiza matrícula a CONFIRMED o CANCELLED y publica EnrollmentUpdatedEvent.
 * TRABAJO_FINAL: enrollment-service consume eventos de pago.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventKafkaListener {

    private final ObjectMapper objectMapper;
    private final UpdateEnrollmentStatusUseCase updateEnrollmentStatusUseCase;
    private final EnrollmentEventPublisher enrollmentEventPublisher;

    @KafkaListener(topics = KafkaConfig.PAYMENT_EVENTS_TOPIC, groupId = "enrollment-service-group")
    public void onPaymentEvent(String payload) {
        try {
            PaymentEventDto event = objectMapper.readValue(payload, PaymentEventDto.class);
            if (event.getEnrollmentId() == null) {
                log.warn("Payment event without enrollmentId, skipping");
                return;
            }
            String newStatus = "APPROVED".equalsIgnoreCase(event.getStatus()) ? "CONFIRMED" : "CANCELLED";
            Enrollment updated = updateEnrollmentStatusUseCase.execute(event.getEnrollmentId(), newStatus);
            enrollmentEventPublisher.publishUpdated(updated);
            log.info("Enrollment {} updated to {} after payment event", event.getEnrollmentId(), newStatus);
        } catch (Exception e) {
            log.error("Error processing payment event payload: {}", payload, e);
        }
    }
}
