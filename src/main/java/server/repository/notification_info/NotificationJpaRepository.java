package server.repository.notification_info;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.notification.persist.NotificationInfo;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationInfo, Long> {
}
