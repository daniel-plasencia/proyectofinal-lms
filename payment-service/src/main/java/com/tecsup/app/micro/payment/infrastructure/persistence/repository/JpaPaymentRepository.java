package com.tecsup.app.micro.payment.infrastructure.persistence.repository;

import com.tecsup.app.micro.payment.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
