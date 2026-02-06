package com.tecsup.app.micro.enrollment.application.usecase;

import com.tecsup.app.micro.enrollment.domain.exception.EnrollmentNotFoundException;
import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Actualiza el estado de una matrícula (p. ej. CONFIRMED / CANCELLED tras evento de pago).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateEnrollmentStatusUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public Enrollment execute(Long enrollmentId, String newStatus) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));
        enrollment.setStatus(newStatus);
        Enrollment updated = enrollmentRepository.save(enrollment);
        log.info("Enrollment {} status updated to {}", enrollmentId, newStatus);
        return updated;
    }
}
