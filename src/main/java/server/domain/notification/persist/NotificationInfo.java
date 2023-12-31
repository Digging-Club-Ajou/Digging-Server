package server.domain.notification.persist;

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
public class NotificationInfo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_info_id")
    private Long id;

    @Association
    private Long memberId;

    private String notificationMessage;

    @Builder
    private NotificationInfo(final long memberId, final String notificationMessage) {
        this.memberId = memberId;
        this.notificationMessage = notificationMessage;
    }
}
