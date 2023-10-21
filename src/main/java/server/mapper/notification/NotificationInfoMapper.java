package server.mapper.notification;

import server.domain.notification.persist.NotificationInfo;
import server.mapper.notification.dto.NotificationResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static server.global.constant.NotificationConstant.MINUTES_UNIT;

public class NotificationInfoMapper {

    private NotificationInfoMapper() {
    }

    public static NotificationInfo toEntity(final long memberId, final String notificationMessage) {
        return NotificationInfo.builder()
                .memberId(memberId)
                .notificationMessage(notificationMessage)
                .build();
    }

    public static NotificationResponse toNotificationResponse(final NotificationInfo notificationInfo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = notificationInfo.getCreatedAt();
        long minutes = ChronoUnit.MINUTES.between(createdAt, now);

        return new NotificationResponse(notificationInfo.getNotificationMessage(), minutes + MINUTES_UNIT);
    }
}
