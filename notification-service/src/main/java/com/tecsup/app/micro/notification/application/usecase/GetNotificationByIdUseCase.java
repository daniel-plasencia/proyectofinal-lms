package com.tecsup.app.micro.notification.application.usecase;

import com.tecsup.app.micro.notification.domain.model.Notification;
import com.tecsup.app.micro.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetNotificationByIdUseCase {

    private final NotificationRepository notificationRepository;

    public Optional<Notification> execute(Long id) {
        return notificationRepository.findById(id);
    }
}
