package com.tecsup.app.micro.payment.application.service;

import com.tecsup.app.micro.payment.application.usecase.CreatePaymentUseCase;
import com.tecsup.app.micro.payment.application.usecase.GetPaymentByIdUseCase;
import com.tecsup.app.micro.payment.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final GetPaymentByIdUseCase getPaymentByIdUseCase;

    @Transactional
    public Payment createPayment(Payment payment) {
        return createPaymentUseCase.execute(payment);
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(Long id) {
        return getPaymentByIdUseCase.execute(id);
    }
}
