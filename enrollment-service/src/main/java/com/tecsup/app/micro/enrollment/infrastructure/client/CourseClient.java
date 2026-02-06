package com.tecsup.app.micro.enrollment.infrastructure.client;

import com.tecsup.app.micro.enrollment.domain.exception.CourseServiceException;
import com.tecsup.app.micro.enrollment.infrastructure.client.dto.CourseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente REST para course-service (RestTemplate).
 * Guía: msv/product-service (UserClient).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CourseClient {

    private final RestTemplate restTemplate;

    @Value("${course.service.url}")
    private String courseServiceUrl;

    public CourseDTO getCourseById(Long courseId) {
        log.info("Calling Course Service to get course with id: {}", courseId);
        String url = courseServiceUrl + "/courses/" + courseId;
        try {
            CourseDTO course = restTemplate.getForObject(url, CourseDTO.class);
            log.info("Course retrieved successfully: {}", course != null ? course.getTitle() : null);
            return course;
        } catch (Exception e) {
            log.error("Error calling Course Service: {}", e.getMessage());
            throw new CourseServiceException("Error calling Course Service: " + e.getMessage(), e);
        }
    }
}
