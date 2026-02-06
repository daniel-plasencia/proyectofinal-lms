package com.tecsup.app.micro.notification.infrastructure.persistence.repository;

import com.tecsup.app.micro.notification.domain.model.Notification;
import com.tecsup.app.micro.notification.domain.repository.NotificationRepository;
import com.tecsup.app.micro.notification.infrastructure.persistence.entity.NotificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final JpaNotificationRepository jpaRepository;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = toEntity(notification);
        NotificationEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public java.util.Optional<Notification> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    private Notification toDomain(NotificationEntity e) {
        return Notification.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .message(e.getMessage())
                .sent(e.getSent())
                .createdAt(e.getCreatedAt())
                .build();
    }

    private NotificationEntity toEntity(Notification n) {
        return NotificationEntity.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .message(n.getMessage())
                .sent(n.getSent() != null ? n.getSent() : false)
                .createdAt(n.getCreatedAt())
                .build();
    }
}
