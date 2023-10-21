package server.repository.notification_info;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.domain.notification.persist.NotificationInfo;
import server.domain.notification.persist.QNotificationInfo;

import java.util.List;

import static server.domain.notification.persist.QNotificationInfo.*;

@Repository
public class NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;
    private final JPAQueryFactory queryFactory;

    public NotificationRepository(final NotificationJpaRepository notificationJpaRepository,
                                  final JPAQueryFactory queryFactory) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.queryFactory = queryFactory;
    }

    public void save(final NotificationInfo notificationInfo) {
        notificationJpaRepository.save(notificationInfo);
    }

    public List<NotificationInfo> findAllByMemberId(final long memberId) {
        return queryFactory
                .selectFrom(notificationInfo)
                .where(notificationInfo.memberId.eq(memberId))
                .orderBy(notificationInfo.createdAt.desc())
                .fetch();
    }
}
