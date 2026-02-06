package com.tecsup.app.micro.course.domain.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(Long id) {
        super("Course not found with id: " + id);
    }
}
