package com.tecsup.app.micro.payment.infrastructure.kafka;

import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.infrastructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishApproved(Payment payment) {
        PaymentApprovedEvent event = new PaymentApprovedEvent(
                payment.getId(),
                payment.getEnrollmentId(),
                payment.getAmount(),
                payment.getStatus()
        );
        send(KafkaConfig.PAYMENT_EVENTS_TOPIC, "approved-" + payment.getId(), event);
        log.info("Published PaymentApprovedEvent for payment id: {}", payment.getId());
    }

    public void publishRejected(Payment payment) {
        PaymentRejectedEvent event = new PaymentRejectedEvent(
                payment.getId(),
                payment.getEnrollmentId(),
                payment.getAmount(),
                payment.getStatus()
        );
        send(KafkaConfig.PAYMENT_EVENTS_TOPIC, "rejected-" + payment.getId(), event);
        log.info("Published PaymentRejectedEvent for payment id: {}", payment.getId());
    }

    private void send(String topic, String key, Object event) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, event);
        future.whenComplete((result, ex) -> {
            if (ex != null) log.error("Failed to send payment event: {}", ex.getMessage());
        });
    }
}
