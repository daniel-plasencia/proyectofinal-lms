package com.tecsup.app.micro.payment.infrastructure.persistence.repository;

import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.PaymentRepository;
import com.tecsup.app.micro.payment.infrastructure.persistence.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public java.util.Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    private Payment toDomain(PaymentEntity e) {
        return Payment.builder()
                .id(e.getId())
                .enrollmentId(e.getEnrollmentId())
                .amount(e.getAmount())
                .status(e.getStatus())
                .paidAt(e.getPaidAt())
                .build();
    }

    private PaymentEntity toEntity(Payment p) {
        return PaymentEntity.builder()
                .id(p.getId())
                .enrollmentId(p.getEnrollmentId())
                .amount(p.getAmount())
                .status(p.getStatus() != null ? p.getStatus() : "APPROVED")
                .paidAt(p.getPaidAt())
                .build();
    }
}
