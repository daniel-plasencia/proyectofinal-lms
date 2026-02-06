package com.tecsup.app.micro.enrollment.domain.exception;

/**
 * Error al llamar a course-service (RestTemplate).
 */
public class CourseServiceException extends RuntimeException {

    public CourseServiceException(String message) {
        super(message);
    }

    public CourseServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
