package com.tecsup.app.micro.course.application.usecase;

import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllCoursesUseCase {

    private final CourseRepository courseRepository;

    public List<Course> execute() {
        log.debug("Executing GetAllCoursesUseCase");
        return courseRepository.findAll();
    }
}
