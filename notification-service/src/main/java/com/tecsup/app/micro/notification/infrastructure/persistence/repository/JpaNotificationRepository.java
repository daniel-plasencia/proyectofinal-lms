package com.tecsup.app.micro.notification.infrastructure.persistence.repository;

import com.tecsup.app.micro.notification.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
