package com.tecsup.app.micro.course.application.usecase;

import com.tecsup.app.micro.course.domain.exception.CourseNotFoundException;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCourseByIdUseCase {

    private final CourseRepository courseRepository;

    public Course execute(Long id) {
        log.debug("Executing GetCourseByIdUseCase for id: {}", id);
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }
}
