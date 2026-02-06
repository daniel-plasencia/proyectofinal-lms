package com.tecsup.app.micro.enrollment.infrastructure.persistence.repository;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.entity.EnrollmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final JpaEnrollmentRepository jpaRepository;

    @Override
    public Enrollment save(Enrollment enrollment) {
        EnrollmentEntity entity = toEntity(enrollment);
        entity = jpaRepository.save(entity);
        return toDomain(entity);
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Enrollment> findByUserId(Long userId) {
        return jpaRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private EnrollmentEntity toEntity(Enrollment d) {
        return EnrollmentEntity.builder()
                .id(d.getId())
                .userId(d.getUserId())
                .courseId(d.getCourseId())
                .status(d.getStatus())
                .createdAt(d.getCreatedAt())
                .build();
    }

    private Enrollment toDomain(EnrollmentEntity e) {
        return Enrollment.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .courseId(e.getCourseId())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .build();
    }
}
