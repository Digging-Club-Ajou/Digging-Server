package server.mapper.notification;

import server.domain.notification.persist.NotificationInfo;
import server.mapper.notification.dto.NotificationResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        if (minutes < 60 * 24) {
            long hours = ChronoUnit.HOURS.between(createdAt, now);
            if (hours > 0) {
                return new NotificationResponse(notificationInfo.getId(),
                        notificationInfo.getNotificationMessage(),
                        hours + "시간");
            } else {
                return new NotificationResponse(notificationInfo.getId(),
                        notificationInfo.getNotificationMessage(),
                        minutes + "분");
            }
        } else if (minutes < 60 * 24 * 7) {
            long days = ChronoUnit.DAYS.between(createdAt, now);
            return new NotificationResponse(notificationInfo.getId(),
                    notificationInfo.getNotificationMessage(),
                    days + "일");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new NotificationResponse(notificationInfo.getId(),
                    notificationInfo.getNotificationMessage(),
                    createdAt.format(formatter));
        }
    }

}
