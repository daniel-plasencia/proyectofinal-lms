package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.payment.domain.exception.InvalidPaymentDataException;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.PaymentRepository;
import com.tecsup.app.micro.payment.infrastructure.kafka.PaymentEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    public Payment execute(Payment payment) {
        log.debug("Executing CreatePaymentUseCase for enrollmentId: {}", payment.getEnrollmentId());
        if (payment.getEnrollmentId() == null || payment.getAmount() == null || payment.getAmount().signum() < 0) {
            throw new InvalidPaymentDataException("enrollmentId and positive amount are required.");
        }
        if (payment.getStatus() == null || payment.getStatus().isBlank()) {
            payment.setStatus("APPROVED");
        }
        payment.setPaidAt(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);

        if ("APPROVED".equalsIgnoreCase(saved.getStatus())) {
            eventPublisher.publishApproved(saved);
        } else {
            eventPublisher.publishRejected(saved);
        }

        log.info("Payment created with id: {}, status: {}", saved.getId(), saved.getStatus());
        return saved;
    }
}
