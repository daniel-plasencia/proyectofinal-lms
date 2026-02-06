package com.tecsup.app.micro.enrollment.application.usecase;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import com.tecsup.app.micro.enrollment.infrastructure.client.CourseClient;
import com.tecsup.app.micro.enrollment.infrastructure.client.UserClient;
import com.tecsup.app.micro.enrollment.infrastructure.kafka.EnrollmentEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: crear matrícula.
 * Valida usuario y curso vía RestTemplate (user-service, course-service) antes de persistir.
 * TRABAJO_FINAL.md: enrollment-service consume user-service y course-service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateEnrollmentUseCase {

    private final UserClient userClient;
    private final CourseClient courseClient;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentEventPublisher eventPublisher;

    public Enrollment execute(Long userId, Long courseId) {
        // Validar que el usuario existe en user-service
        userClient.getUserById(userId);
        // Validar que el curso existe en course-service
        courseClient.getCourseById(courseId);

        Enrollment enrollment = Enrollment.builder()
                .userId(userId)
                .courseId(courseId)
                .status("PENDING_PAYMENT")
                .build();
        Enrollment saved = enrollmentRepository.save(enrollment);
        eventPublisher.publishCreated(saved);
        log.info("Enrollment created: id={}, userId={}, courseId={}", saved.getId(), userId, courseId);
        return saved;
    }
}
