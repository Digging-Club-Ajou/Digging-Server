package server.mapper.push_alarm;

import server.domain.push_alarm.PushAlarm;

public enum PushAlarmMapper {

    PushAlarmMapper() {
    };

    public static PushAlarm toEntityTargetToken(final long memberId, final String targetToken) {
        return PushAlarm.builder()
                .memberId(memberId)
                .targetToken(targetToken)
                .build();
    }
}
