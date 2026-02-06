package com.tecsup.app.micro.payment.infrastructure.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRejectedEvent {

    private Long paymentId;
    private Long enrollmentId;
    private BigDecimal amount;
    private String status;
}
