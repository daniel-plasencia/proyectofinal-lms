package com.tecsup.app.micro.enrollment.infrastructure.kafka;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.infrastructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnrollmentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(Enrollment enrollment) {
        EnrollmentCreatedEvent event = new EnrollmentCreatedEvent(
                "EnrollmentCreatedEvent",
                enrollment.getUserId(),
                enrollment.getId(),
                enrollment.getCourseId(),
                enrollment.getStatus()
        );
        send(KafkaConfig.ENROLLMENT_EVENTS_TOPIC, "created-" + enrollment.getId(), event);
        log.info("Published EnrollmentCreatedEvent for enrollment id: {}", enrollment.getId());
    }

    public void publishUpdated(Enrollment enrollment) {
        EnrollmentUpdatedEvent event = new EnrollmentUpdatedEvent(
                "EnrollmentUpdatedEvent",
                enrollment.getUserId(),
                enrollment.getId(),
                enrollment.getCourseId(),
                enrollment.getStatus()
        );
        send(KafkaConfig.ENROLLMENT_EVENTS_TOPIC, "updated-" + enrollment.getId(), event);
        log.info("Published EnrollmentUpdatedEvent for enrollment id: {}", enrollment.getId());
    }

    private void send(String topic, String key, Object event) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, event);
        future.whenComplete((result, ex) -> {
            if (ex != null) log.error("Failed to send enrollment event: {}", ex.getMessage());
        });
    }
}
