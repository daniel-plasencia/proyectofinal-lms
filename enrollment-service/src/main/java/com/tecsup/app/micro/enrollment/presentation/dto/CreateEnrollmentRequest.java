package com.tecsup.app.micro.enrollment.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnrollmentRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "courseId is required")
    private Long courseId;
}
