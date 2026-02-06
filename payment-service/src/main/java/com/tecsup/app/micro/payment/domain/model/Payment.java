package com.tecsup.app.micro.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;
    private Long enrollmentId;
    private BigDecimal amount;
    private String status;  // APPROVED, REJECTED
    private LocalDateTime paidAt;
}
