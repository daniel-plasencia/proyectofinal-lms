package com.tecsup.app.micro.course.domain.repository;

import com.tecsup.app.micro.course.domain.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    List<Course> findAll();

    Optional<Course> findById(Long id);

    Course save(Course course);
}
