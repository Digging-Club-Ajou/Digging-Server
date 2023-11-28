package server.repository.following;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.following.FollowingInfo;

import java.util.List;

public interface FollowingJpaRepository extends JpaRepository<FollowingInfo, Long> {

    List<FollowingInfo> findAllByFollowedId(final Long followedId);

    List<FollowingInfo> findAllByFollowingIdOrderByLastModifiedAtDesc(final Long followingId);

    FollowingInfo findByFollowingIdAndAndFollowedId(final Long followingId, Long followedId);

}
