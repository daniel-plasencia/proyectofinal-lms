package com.tecsup.app.micro.course.application.usecase;

import com.tecsup.app.micro.course.domain.event.CoursePublishedEvent;
import com.tecsup.app.micro.course.domain.exception.CourseNotFoundException;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import com.tecsup.app.micro.course.infrastructure.kafka.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublishCourseUseCase {

    private final CourseRepository courseRepository;
    private final KafkaEventPublisher eventPublisher;

    public Course execute(Long id) {
        log.debug("Executing PublishCourseUseCase for id: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        course.setPublished(true);
        Course updated = courseRepository.save(course);

        eventPublisher.publish(new CoursePublishedEvent(
                updated.getId().toString(),
                updated.getTitle(),
                updated.getDescription()
        ));

        log.info("Course published successfully with id: {}", id);
        return updated;
    }
}
