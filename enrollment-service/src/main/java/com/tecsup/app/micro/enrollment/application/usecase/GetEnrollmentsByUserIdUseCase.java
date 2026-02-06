package com.tecsup.app.micro.enrollment.application.usecase;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: listar matrículas por usuario.
 * TRABAJO_FINAL.md: GET /enrollments?userId=
 */
@Service
@RequiredArgsConstructor
public class GetEnrollmentsByUserIdUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public List<Enrollment> execute(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }
}
