package com.tecsup.app.micro.course.infrastructure.persistence.repository;

import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import com.tecsup.app.micro.course.infrastructure.persistence.entity.CourseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CourseRepositoryImpl implements CourseRepository {

    private final JpaCourseRepository jpaCourseRepository;

    @Override
    public List<Course> findAll() {
        return jpaCourseRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaCourseRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = toEntity(course);
        CourseEntity saved = jpaCourseRepository.save(entity);
        return toDomain(saved);
    }

    private Course toDomain(CourseEntity entity) {
        return Course.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .published(entity.getPublished())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private CourseEntity toEntity(Course course) {
        return CourseEntity.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .published(course.getPublished() != null ? course.getPublished() : false)
                .createdAt(course.getCreatedAt())
                .build();
    }
}
