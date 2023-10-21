package server.controller.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.notification.dto.NotificationResult;
import server.service.notification.NotificationFindService;


@RequestMapping("/api")
@RestController
public class NotificationController {

    private final NotificationFindService notificationFindService;

    public NotificationController(final NotificationFindService notificationFindService) {
        this.notificationFindService = notificationFindService;
    }

    @GetMapping("/notifications")
    public NotificationResult findAllNotificationResponses(@Login final MemberSession memberSession) {
        return notificationFindService.findNotifications(memberSession.id());
    }
}
