package com.tecsup.app.micro.notification.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Kafka config (README_KAFKA.md + TRABAJO_FINAL.md Tabla 3).
 * notification-service solo consume: lms.payment.events, lms.course.events, lms.enrollment.events.
 */
@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String PAYMENT_EVENTS_TOPIC = "lms.payment.events";
    public static final String COURSE_EVENTS_TOPIC = "lms.course.events";
    public static final String ENROLLMENT_EVENTS_TOPIC = "lms.enrollment.events";

    @Bean
    public NewTopic paymentEventsTopic() {
        return new NewTopic(PAYMENT_EVENTS_TOPIC, 3, (short) 1);
    }

    @Bean
    public NewTopic courseEventsTopic() {
        return new NewTopic(COURSE_EVENTS_TOPIC, 3, (short) 1);
    }

    @Bean
    public NewTopic enrollmentEventsTopic() {
        return new NewTopic(ENROLLMENT_EVENTS_TOPIC, 3, (short) 1);
    }
}
