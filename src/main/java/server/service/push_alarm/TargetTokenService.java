package server.service.push_alarm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.push_alarm.PushAlarm;
import server.mapper.push_alarm.PushAlarmMapper;
import server.repository.push_alarm.PushAlarmRepository;

import java.util.Optional;

@Service
public class TargetTokenService {

    private final PushAlarmRepository pushAlarmRepository;

    public TargetTokenService(final PushAlarmRepository pushAlarmRepository) {
        this.pushAlarmRepository = pushAlarmRepository;
    }

    @Transactional
    public void save(final long memberId, final String targetToken) {
        Optional<PushAlarm> optionalPushAlarm = pushAlarmRepository.findByMemberId(memberId);

        if (optionalPushAlarm.isPresent()) {
            PushAlarm pushAlarm = optionalPushAlarm.get();
            pushAlarm.updateTargetToken(targetToken);
        } else {
            PushAlarm pushAlarm = PushAlarmMapper.toEntityTargetToken(memberId, targetToken);
            pushAlarmRepository.save(pushAlarm);
        }
    }
}
