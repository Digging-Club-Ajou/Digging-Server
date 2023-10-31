package server.repository.following;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.following.FollowingInfo;

import java.util.List;
import java.util.Optional;

public interface FollowingJpaRepository extends JpaRepository<FollowingInfo, Long> {

    List<FollowingInfo> findAllByFollowedId(final Long followedId);

    List<FollowingInfo> findAllByFollowingId(final Long followingId);

}
