package server.repository.push_alarm;

import org.springframework.stereotype.Repository;
import server.domain.push_alarm.PushAlarm;

import java.util.Optional;

@Repository
public class PushAlarmRepository {

    private final PushAlarmJpaRepository pushAlarmJpaRepository;

    public PushAlarmRepository(final PushAlarmJpaRepository pushAlarmJpaRepository) {
        this.pushAlarmJpaRepository = pushAlarmJpaRepository;
    }

    public void save(final PushAlarm pushAlarm) {
        pushAlarmJpaRepository.save(pushAlarm);
    }

    public Optional<PushAlarm> findByMemberId(final long memberId) {
        return pushAlarmJpaRepository.findByMemberId(memberId);
    }
}
