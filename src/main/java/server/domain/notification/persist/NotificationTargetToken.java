package server.domain.notification.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NotificationTargetToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_target_token_id")
    private Long id;

    @Association
    private Long memberId;

    private String targetToken;

    @Builder
    private NotificationTargetToken(final long memberId, final String targetToken) {
        this.memberId = memberId;
        this.targetToken = targetToken;
    }
}
