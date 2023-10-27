package server.repository.following;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.following.FollowingInfo;

import java.util.Optional;

public interface FollowingJpaRepository extends JpaRepository<FollowingInfo, Long> {

    Optional<FollowingInfo> findAllByFollowedId(final Long followedId);
}
