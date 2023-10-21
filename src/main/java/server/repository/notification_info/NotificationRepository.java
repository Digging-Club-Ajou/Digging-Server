package server.repository.notification_info;

import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    public NotificationRepository(final NotificationJpaRepository notificationJpaRepository) {
        this.notificationJpaRepository = notificationJpaRepository;
    }
}
