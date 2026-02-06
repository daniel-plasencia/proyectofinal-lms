package com.tecsup.app.micro.course.presentation.mapper;

import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.presentation.dto.CreateCourseRequest;
import com.tecsup.app.micro.course.presentation.dto.CourseResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseDtoMapper {

    Course toDomain(CreateCourseRequest request);

    CourseResponse toResponse(Course course);

    List<CourseResponse> toResponseList(List<Course> courses);
}
