package server.repository.push_alarm;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.push_alarm.PushAlarm;

import java.util.Optional;

public interface PushAlarmJpaRepository extends JpaRepository<PushAlarm, Long> {

    Optional<PushAlarm> findByMemberId(final long memberId);
}
