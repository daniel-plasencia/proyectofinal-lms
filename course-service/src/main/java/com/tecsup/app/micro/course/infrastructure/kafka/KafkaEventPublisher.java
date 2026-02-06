package com.tecsup.app.micro.course.infrastructure.kafka;

import com.tecsup.app.micro.course.domain.event.DomainEvent;
import com.tecsup.app.micro.course.infrastructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(DomainEvent event) {
        log.info("Publishing event: {} [{}]", event.getEventType(), event.getEventId());
        String key = event.getKey();
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(KafkaConfig.COURSE_EVENTS_TOPIC, key, event);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send event to Kafka: {}", ex.getMessage(), ex);
            } else {
                log.info("Event sent to Kafka - partition: {}, offset: {}",
                        result != null ? result.getRecordMetadata().partition() : null,
                        result != null ? result.getRecordMetadata().offset() : null);
            }
        });
    }
}
