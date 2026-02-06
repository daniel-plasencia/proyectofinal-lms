package com.tecsup.app.micro.enrollment.presentation.controller;

import com.tecsup.app.micro.enrollment.domain.exception.CourseServiceException;
import com.tecsup.app.micro.enrollment.domain.exception.EnrollmentNotFoundException;
import com.tecsup.app.micro.enrollment.domain.exception.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEnrollmentNotFound(EnrollmentNotFoundException ex) {
        log.warn("Enrollment not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<Map<String, String>> handleUserService(UserServiceException ex) {
        log.error("User service error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "User service unavailable: " + ex.getMessage()));
    }

    @ExceptionHandler(CourseServiceException.class)
    public ResponseEntity<Map<String, String>> handleCourseService(CourseServiceException ex) {
        log.error("Course service error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "Course service unavailable: " + ex.getMessage()));
    }
}
