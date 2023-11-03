package server.controller.notification;

import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.notification.dto.NotificationResult;
import server.service.notification.NotificationDeleteService;
import server.service.notification.NotificationFindService;

@RequestMapping("/api")
@RestController
public class NotificationController {

    private final NotificationFindService notificationFindService;
    private final NotificationDeleteService notificationDeleteService;

    public NotificationController(final NotificationFindService notificationFindService,
                                  final NotificationDeleteService notificationDeleteService) {
        this.notificationFindService = notificationFindService;
        this.notificationDeleteService = notificationDeleteService;
    }

    @GetMapping("/notifications")
    public NotificationResult findAllNotificationResponses(@Login final MemberSession memberSession) {
        return notificationFindService.findNotifications(memberSession.id());
    }

    @DeleteMapping("/notifications/{notificationId}")
    public void deleteNotification(@Login final MemberSession memberSession,
                                   @PathVariable final long notificationId) {
        notificationDeleteService.deleteNotification(notificationId);
    }
}
