package com.tecsup.app.micro.course.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Course Domain Model (TRABAJO_FINAL: title, description, published, created_at)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private Long id;
    private String title;
    private String description;
    private Boolean published;
    private LocalDateTime createdAt;

    public boolean isValid() {
        return title != null && !title.trim().isEmpty();
    }
}
