package server.controller.push_alarm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.push_alarm.dto.PushAlarmInfo;
import server.mapper.push_alarm.dto.PushAlarmResponse;
import server.mapper.push_alarm.dto.TargetToken;
import server.service.push_alarm.PushAlarmService;
import server.service.push_alarm.TargetTokenService;

@RequestMapping("/api")
@RestController
public class PushAlarmController {

    private final PushAlarmService pushAlarmService;
    private final TargetTokenService targetTokenService;

    public PushAlarmController(final PushAlarmService pushAlarmService,
                               final TargetTokenService targetTokenService) {
        this.pushAlarmService = pushAlarmService;
        this.targetTokenService = targetTokenService;
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

    @PostMapping("/push-alarm/target-token")
    public ResponseEntity<Void> updateTargetToken(@Login final MemberSession memberSession,
                                                  @RequestBody final TargetToken targetToken) {
        targetTokenService.save(memberSession.id(), targetToken.targetToken());
        return ResponseEntity.noContent().build();
    }
}
