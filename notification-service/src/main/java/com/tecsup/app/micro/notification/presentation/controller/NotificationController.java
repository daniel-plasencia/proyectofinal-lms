package com.tecsup.app.micro.notification.presentation.controller;

import com.tecsup.app.micro.notification.application.usecase.GetNotificationByIdUseCase;
import com.tecsup.app.micro.notification.domain.model.Notification;
import com.tecsup.app.micro.notification.presentation.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final GetNotificationByIdUseCase getNotificationByIdUseCase;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id) {
        Optional<Notification> opt = getNotificationByIdUseCase.execute(id);
        return opt.map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .message(n.getMessage())
                .sent(n.getSent())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
