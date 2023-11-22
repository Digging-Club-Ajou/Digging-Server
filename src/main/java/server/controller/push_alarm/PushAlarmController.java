package server.controller.push_alarm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.push_alarm.PushAlarmInfo;
import server.mapper.push_alarm.PushAlarmResponse;
import server.service.push_alarm.PushAlarmService;

@RequestMapping("/api")
@RestController
public class PushAlarmController {

    private final PushAlarmService pushAlarmService;

    public PushAlarmController(final PushAlarmService pushAlarmService) {
        this.pushAlarmService = pushAlarmService;
    }

    @GetMapping("/push-alarm")
    public PushAlarmResponse getPushAlarm(@Login final MemberSession memberSession) {
        return pushAlarmService.getPushAlarmInfo(memberSession.id());
    }

    @PostMapping("/push-alarm")
    public ResponseEntity<Void> updatePushAlarm(@Login final MemberSession memberSession,
                                                @RequestBody final PushAlarmInfo pushAlarmInfo) {
        pushAlarmService.updatePushAlarm(memberSession.id(), pushAlarmInfo.pushAlarm());
        return ResponseEntity.noContent().build();
    }
}
