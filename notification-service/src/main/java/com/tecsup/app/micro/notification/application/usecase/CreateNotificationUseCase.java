package com.tecsup.app.micro.notification.application.usecase;

import com.tecsup.app.micro.notification.domain.model.Notification;
import com.tecsup.app.micro.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNotificationUseCase {

    private final NotificationRepository notificationRepository;

    public Notification execute(Long userId, String message) {
        Notification notification = Notification.builder()
                .userId(userId != null ? userId : 0L)
                .message(message)
                .sent(false)
                .build();
        return notificationRepository.save(notification);
    }
}
