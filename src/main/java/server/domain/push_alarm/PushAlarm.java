package server.domain.push_alarm;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.BaseTimeEntity;
import server.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PushAlarm extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "push_alarm_id")
    private Long id;

    @Association
    private Long memberId;

    private Boolean pushAlarm;

    private String targetToken;

    @Builder
    private PushAlarm(final Long memberId, final Boolean pushAlarm, final String targetToken) {
        this.memberId = memberId;
        this.pushAlarm = pushAlarm;
        this.targetToken = targetToken;
    }

    public void updatePushAlarmInfo(final Boolean pushAlarm) {
        this.pushAlarm = pushAlarm;
    }

    public void updateTargetToken(final String targetToken) {
        this.targetToken = targetToken;
    }
}
