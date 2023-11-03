package server.repository.notification_info;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.domain.notification.persist.NotificationInfo;
import server.domain.notification.persist.QNotificationInfo;
import server.global.constant.ExceptionMessage;
import server.global.exception.NotFoundException;

import java.util.List;

import static server.domain.notification.persist.QNotificationInfo.*;
import static server.global.constant.ExceptionMessage.*;

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

    public NotificationInfo getById(final long notificationInfoId) {
        return notificationJpaRepository.findById(notificationInfoId)
                .orElseThrow(() -> new NotFoundException(NOTIFICATION_INFO_NOT_FOUND_EXCEPTION.message));
    }

    public List<NotificationInfo> findAllByMemberId(final long memberId) {
        return queryFactory
                .selectFrom(notificationInfo)
                .where(notificationInfo.memberId.eq(memberId))
                .orderBy(notificationInfo.createdAt.desc())
                .fetch();
    }

    public void delete(final NotificationInfo notificationInfo) {
        notificationJpaRepository.delete(notificationInfo);
    }
}
