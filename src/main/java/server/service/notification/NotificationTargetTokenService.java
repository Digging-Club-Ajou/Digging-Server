package server.service.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.notification.persist.NotificationTargetToken;
import server.repository.notification_info.NotificationTargetTokenRepository;

@Service
public class NotificationTargetTokenService {

    private final NotificationTargetTokenRepository notificationTargetTokenRepository;

    public NotificationTargetTokenService(final NotificationTargetTokenRepository notificationTargetTokenRepository) {
        this.notificationTargetTokenRepository = notificationTargetTokenRepository;
    }

    @Transactional
    public void save(final long memberId, final String targetToken) {
        NotificationTargetToken notificationTargetToken = NotificationTargetToken.builder()
                .memberId(memberId)
                .targetToken(targetToken)
                .build();

        notificationTargetTokenRepository.save(notificationTargetToken);
    }
}
