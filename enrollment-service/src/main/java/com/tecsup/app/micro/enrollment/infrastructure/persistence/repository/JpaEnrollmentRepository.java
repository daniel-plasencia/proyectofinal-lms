package com.tecsup.app.micro.enrollment.infrastructure.persistence.repository;

import com.tecsup.app.micro.enrollment.infrastructure.persistence.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    List<EnrollmentEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}
