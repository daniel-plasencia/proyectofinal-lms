package com.tecsup.app.micro.course.presentation.controller;

import com.tecsup.app.micro.course.application.service.CourseApplicationService;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.presentation.dto.CourseResponse;
import com.tecsup.app.micro.course.presentation.dto.CreateCourseRequest;
import com.tecsup.app.micro.course.presentation.mapper.CourseDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseApplicationService courseApplicationService;
    private final CourseDtoMapper courseDtoMapper;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        log.info("REST request to create course: {}", request.getTitle());
        Course course = courseDtoMapper.toDomain(request);
        Course created = courseApplicationService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseDtoMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        log.info("REST request to get all courses");
        List<Course> courses = courseApplicationService.getAllCourses();
        return ResponseEntity.ok(courseDtoMapper.toResponseList(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        log.info("REST request to get course by id: {}", id);
        Course course = courseApplicationService.getCourseById(id);
        return ResponseEntity.ok(courseDtoMapper.toResponse(course));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<CourseResponse> publishCourse(@PathVariable Long id) {
        log.info("REST request to publish course with id: {}", id);
        Course course = courseApplicationService.publishCourse(id);
        return ResponseEntity.ok(courseDtoMapper.toResponse(course));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Course Service running!");
    }
}
