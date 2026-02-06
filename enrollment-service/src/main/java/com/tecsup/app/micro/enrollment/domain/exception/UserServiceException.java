package com.tecsup.app.micro.enrollment.domain.exception;

/**
 * Error al llamar a user-service (RestTemplate).
 */
public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
