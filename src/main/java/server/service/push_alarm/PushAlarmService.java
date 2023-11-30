package server.service.push_alarm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.push_alarm.PushAlarm;
import server.mapper.push_alarm.dto.PushAlarmResponse;
import server.repository.push_alarm.PushAlarmRepository;

import java.util.Optional;

@Service
public class PushAlarmService {

    private final PushAlarmRepository pushAlarmRepository;

    public PushAlarmService(final PushAlarmRepository pushAlarmRepository) {
        this.pushAlarmRepository = pushAlarmRepository;
    }

    @Transactional
    public void savePushAlarm(final long memberId) {
        PushAlarm pushAlarm = PushAlarm.builder()
                .memberId(memberId)
                .pushAlarm(true)
                .build();

        pushAlarmRepository.save(pushAlarm);
    }

    @Transactional
    public void updatePushAlarm(final long memberId, final Boolean pushAlarmInfo) {
        Optional<PushAlarm> optionalPushAlarm = pushAlarmRepository.findByMemberId(memberId);
        if (optionalPushAlarm.isPresent()) {
            PushAlarm pushAlarm = optionalPushAlarm.get();
            pushAlarm.updatePushAlarmInfo(pushAlarmInfo);
        } else {
            PushAlarm pushAlarm = PushAlarm.builder()
                    .memberId(memberId)
                    .pushAlarm(pushAlarmInfo)
                    .build();

            pushAlarmRepository.save(pushAlarm);
        }
    }

    @Transactional(readOnly = true)
    public PushAlarmResponse getPushAlarmInfo(final long memberId) {
        Optional<PushAlarm> optionalPushAlarm = pushAlarmRepository.findByMemberId(memberId);
        if (optionalPushAlarm.isPresent()) {
            PushAlarm pushAlarm = optionalPushAlarm.get();
            Boolean pushAlarmInfo = pushAlarm.getPushAlarm();
            return new PushAlarmResponse(pushAlarmInfo);
        }
        return new PushAlarmResponse(false);
    }
}
