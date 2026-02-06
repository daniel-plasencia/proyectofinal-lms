package com.tecsup.app.micro.enrollment.domain.repository;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;

import java.util.List;
import java.util.Optional;

/**
 * Puerto del repositorio de matrículas.
 */
public interface EnrollmentRepository {

    Enrollment save(Enrollment enrollment);

    Optional<Enrollment> findById(Long id);

    List<Enrollment> findByUserId(Long userId);
}
