package com.tecsup.app.micro.enrollment.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Kafka topics (TRABAJO_FINAL.md Tabla 3).
 * enrollment-service publica en lms.enrollment.events y consume lms.payment.events.
 */
@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String ENROLLMENT_EVENTS_TOPIC = "lms.enrollment.events";
    public static final String PAYMENT_EVENTS_TOPIC = "lms.payment.events";

    @Bean
    public NewTopic enrollmentEventsTopic() {
        return new NewTopic(ENROLLMENT_EVENTS_TOPIC, 3, (short) 1);
    }

    @Bean
    public NewTopic paymentEventsTopic() {
        return new NewTopic(PAYMENT_EVENTS_TOPIC, 3, (short) 1);
    }
}
