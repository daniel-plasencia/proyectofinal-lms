package com.tecsup.app.micro.course.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * KafkaConfig (README_KAFKA.md § II.3).
 * Topic: lms.course.events (TRABAJO_FINAL Tabla 3 - opcional).
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
