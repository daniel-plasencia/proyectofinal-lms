package com.tecsup.app.micro.course.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Evento de dominio base para Kafka (guía README_KAFKA).
 * Las subclases implementan getKey() para el particionado.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class DomainEvent {

    private String eventId;
    private String eventType;
    private LocalDateTime occurredOn;

    public DomainEvent(String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.occurredOn = LocalDateTime.now();
    }

    /**
     * Clave para particionado en Kafka (p. ej. courseId).
     */
    public String getKey() {
        throw new UnsupportedOperationException("getKey() must be implemented by subclass");
    }
}
