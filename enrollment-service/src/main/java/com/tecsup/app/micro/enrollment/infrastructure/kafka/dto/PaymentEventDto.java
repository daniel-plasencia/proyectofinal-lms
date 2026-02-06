package com.tecsup.app.micro.enrollment.infrastructure.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para deserializar eventos de lms.payment.events (PaymentApprovedEvent / PaymentRejectedEvent).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentEventDto {

    private Long paymentId;
    private Long enrollmentId;
    private BigDecimal amount;
    private String status;
}
