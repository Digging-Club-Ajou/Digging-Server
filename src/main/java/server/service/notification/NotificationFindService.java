package server.service.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.notification.persist.NotificationInfo;
import server.mapper.notification.NotificationInfoMapper;
import server.mapper.notification.dto.NotificationResponse;
import server.mapper.notification.dto.NotificationResult;
import server.repository.notification_info.NotificationRepository;

import java.util.List;

@Service
public class NotificationFindService {

    private final NotificationRepository notificationRepository;

    public NotificationFindService(final NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public NotificationResult findNotifications(final long memberId) {
        List<NotificationInfo> notificationInfos = notificationRepository.findAllByMemberId(memberId);

        List<NotificationResponse> notificationResponses = notificationInfos
                .stream()
                .map(NotificationInfoMapper::toNotificationResponse)
                .toList();

        return new NotificationResult(notificationResponses);
    }
}
