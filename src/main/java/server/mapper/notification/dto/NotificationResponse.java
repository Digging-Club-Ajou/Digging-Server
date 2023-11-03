package server.mapper.notification.dto;

public record NotificationResponse(
        long notificationId,
        String message,
        String minutes
) {
}
