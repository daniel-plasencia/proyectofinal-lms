package com.tecsup.app.micro.enrollment.presentation.controller;

import com.tecsup.app.micro.enrollment.application.usecase.CreateEnrollmentUseCase;
import com.tecsup.app.micro.enrollment.application.usecase.GetEnrollmentByIdUseCase;
import com.tecsup.app.micro.enrollment.application.usecase.GetEnrollmentsByUserIdUseCase;
import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.presentation.dto.CreateEnrollmentRequest;
import com.tecsup.app.micro.enrollment.presentation.dto.EnrollmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST de matrículas.
 * TRABAJO_FINAL.md: POST /enrollments, GET /enrollments/{id}, GET /enrollments?userId=
 */
@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {

    private final CreateEnrollmentUseCase createEnrollmentUseCase;
    private final GetEnrollmentByIdUseCase getEnrollmentByIdUseCase;
    private final GetEnrollmentsByUserIdUseCase getEnrollmentsByUserIdUseCase;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> createEnrollment(@Valid @RequestBody CreateEnrollmentRequest request) {
        log.info("REST request to create enrollment: userId={}, courseId={}", request.getUserId(), request.getCourseId());
        Enrollment enrollment = createEnrollmentUseCase.execute(request.getUserId(), request.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(enrollment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollmentById(@PathVariable Long id) {
        log.info("REST request to get enrollment by id: {}", id);
        Enrollment enrollment = getEnrollmentByIdUseCase.execute(id);
        return ResponseEntity.ok(toResponse(enrollment));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getEnrollmentsByUserId(@RequestParam(required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        log.info("REST request to get enrollments by userId: {}", userId);
        List<Enrollment> enrollments = getEnrollmentsByUserIdUseCase.execute(userId);
        List<EnrollmentResponse> body = enrollments.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Enrollment Service running!");
    }

    private EnrollmentResponse toResponse(Enrollment e) {
        return EnrollmentResponse.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .courseId(e.getCourseId())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .build();
    }
}
