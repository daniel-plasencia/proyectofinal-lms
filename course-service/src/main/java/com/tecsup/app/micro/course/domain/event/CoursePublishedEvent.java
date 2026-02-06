package com.tecsup.app.micro.course.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Evento emitido cuando un curso se publica (TRABAJO_FINAL - opcional).
 */
@Getter
@Setter
@NoArgsConstructor
public class CoursePublishedEvent extends DomainEvent {

    private String courseId;
    private String title;
    private String description;

    public CoursePublishedEvent(String courseId, String title, String description) {
        super("CoursePublishedEvent");
        this.courseId = courseId;
        this.title = title;
        this.description = description;
    }

    @Override
    public String getKey() {
        return courseId;
    }
}
