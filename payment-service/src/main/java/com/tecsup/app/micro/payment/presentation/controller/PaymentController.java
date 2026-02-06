package com.tecsup.app.micro.payment.presentation.controller;

import com.tecsup.app.micro.payment.application.service.PaymentApplicationService;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.presentation.dto.PaymentResponse;
import com.tecsup.app.micro.payment.presentation.dto.CreatePaymentRequest;
import com.tecsup.app.micro.payment.presentation.mapper.PaymentDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;
    private final PaymentDtoMapper paymentDtoMapper;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        log.info("REST request to create payment for enrollmentId: {}", request.getEnrollmentId());
        Payment payment = paymentDtoMapper.toDomain(request);
        Payment created = paymentApplicationService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentDtoMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        log.info("REST request to get payment by id: {}", id);
        Payment payment = paymentApplicationService.getPaymentById(id);
        return ResponseEntity.ok(paymentDtoMapper.toResponse(payment));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service running!");
    }
}
