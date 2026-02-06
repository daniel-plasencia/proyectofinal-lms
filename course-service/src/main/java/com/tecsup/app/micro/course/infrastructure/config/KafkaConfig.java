package com.tecsup.app.micro.course.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Configuración de Kafka (TRABAJO_FINAL - evento opcional CoursePublishedEvent).
 * Topic: lms.course.events
 */
@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String COURSE_EVENTS_TOPIC = "lms.course.events";

    @Bean
    public NewTopic courseEventsTopic() {
        return new NewTopic(COURSE_EVENTS_TOPIC, 3, (short) 1);
    }
}
