package com.tecsup.app.micro.course.application.service;

import com.tecsup.app.micro.course.application.usecase.*;
import com.tecsup.app.micro.course.domain.model.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseApplicationService {

    private final GetAllCoursesUseCase getAllCoursesUseCase;
    private final GetCourseByIdUseCase getCourseByIdUseCase;
    private final CreateCourseUseCase createCourseUseCase;
    private final PublishCourseUseCase publishCourseUseCase;

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return getAllCoursesUseCase.execute();
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return getCourseByIdUseCase.execute(id);
    }

    @Transactional
    public Course createCourse(Course course) {
        return createCourseUseCase.execute(course);
    }

    @Transactional
    public Course publishCourse(Long id) {
        return publishCourseUseCase.execute(id);
    }
}
