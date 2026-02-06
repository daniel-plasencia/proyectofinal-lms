package com.tecsup.app.micro.enrollment.domain.exception;

/**
 * Excepción cuando no existe la matrícula.
 */
public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(Long id) {
        super("Enrollment not found with id: " + id);
    }
}
