package com.tecsup.app.micro.course.infrastructure.persistence.repository;

import com.tecsup.app.micro.course.infrastructure.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCourseRepository extends JpaRepository<CourseEntity, Long> {
}
