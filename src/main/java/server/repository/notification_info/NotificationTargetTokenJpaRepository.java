package server.repository.notification_info;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.notification.persist.NotificationTargetToken;

public interface NotificationTargetTokenJpaRepository extends JpaRepository<NotificationTargetToken, Long> {
}
