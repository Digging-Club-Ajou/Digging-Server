package server.repository.notification_info;

import org.springframework.stereotype.Repository;
import server.domain.notification.persist.NotificationTargetToken;

@Repository
public class NotificationTargetTokenRepository {

    private final NotificationTargetTokenJpaRepository notificationTargetTokenJpaRepository;

    public NotificationTargetTokenRepository(
            final NotificationTargetTokenJpaRepository notificationTargetTokenJpaRepository
    ) {
        this.notificationTargetTokenJpaRepository = notificationTargetTokenJpaRepository;
    }

    public void save(final NotificationTargetToken notificationTargetToken) {
        notificationTargetTokenJpaRepository.save(notificationTargetToken);
    }
}
