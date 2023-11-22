package server.controller.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.notification.dto.NotificationResult;
import server.mapper.notification.dto.PushAlarmInfo;
import server.service.notification.NotificationDeleteService;
import server.service.notification.NotificationFindService;
import server.service.push_alarm.PushAlarmService;

@RequestMapping("/api")
@RestController
public class NotificationController {

    private final NotificationFindService notificationFindService;
    private final NotificationDeleteService notificationDeleteService;
    private final PushAlarmService pushAlarmService;

    public NotificationController(final NotificationFindService notificationFindService,
                                  final NotificationDeleteService notificationDeleteService,
                                  final PushAlarmService pushAlarmService) {
        this.notificationFindService = notificationFindService;
        this.notificationDeleteService = notificationDeleteService;
        this.pushAlarmService = pushAlarmService;
    }

    @GetMapping("/notifications")
    public NotificationResult findAllNotificationResponses(@Login final MemberSession memberSession) {
        return notificationFindService.findNotifications(memberSession.id());
    }

    @PostMapping("/notifications")
    public ResponseEntity<Void> updatePushAlarm(@Login final MemberSession memberSession,
                                                @RequestBody final PushAlarmInfo pushAlarmInfo) {
        pushAlarmService.updatePushAlarm(memberSession.id(), pushAlarmInfo.pushAlarm());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/notifications/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@Login final MemberSession memberSession,
                                                   @PathVariable final long notificationId) {
        notificationDeleteService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
