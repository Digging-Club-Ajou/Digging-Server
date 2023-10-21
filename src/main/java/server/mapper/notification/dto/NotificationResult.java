package server.mapper.notification.dto;

import java.util.List;

public record NotificationResult(
        List<NotificationResponse> notificationResponses
) {
}
