package server.service.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.notification.persist.NotificationInfo;
import server.repository.notification_info.NotificationRepository;

@Service
public class NotificationDeleteService {

    private final NotificationRepository notificationRepository;

    public NotificationDeleteService(final NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void deleteNotification(final long notificationId) {
        NotificationInfo notificationInfo = notificationRepository.getById(notificationId);
        notificationRepository.delete(notificationInfo);
    }
}
