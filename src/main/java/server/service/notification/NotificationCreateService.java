package server.service.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.notification.persist.NotificationInfo;
import server.mapper.notification.NotificationInfoMapper;
import server.repository.notification_info.NotificationRepository;

@Service
public class NotificationCreateService {

    private final NotificationRepository notificationRepository;

    public NotificationCreateService(final NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void saveNotificationMessage(final long memberId, final String notificationMessage) {
        NotificationInfo notificationInfo = NotificationInfoMapper.toEntity(memberId, notificationMessage);
        notificationRepository.save(notificationInfo);
    }
}
