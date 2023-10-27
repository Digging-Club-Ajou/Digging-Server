package server.repository.following;

import org.springframework.stereotype.Repository;
import server.domain.following.FollowingInfo;
import server.domain.genre.Genre;
import server.domain.member.persist.Member;

import java.util.Optional;

@Repository
public class FollowingRepository {

    private FollowingJpaRepository followingJpaRepository;

    public FollowingRepository(final FollowingJpaRepository followingJpaRepository){
        this.followingJpaRepository = followingJpaRepository;
    }

    public void save(final FollowingInfo followingInfo){
        followingJpaRepository.save(followingInfo);
    }

    public Optional<FollowingInfo> findAllByFollowedId(final Long followedId) {
        return followingJpaRepository.findAllByFollowedId(followedId);
    }

    public Optional<FollowingInfo> findAllByFollowingId(final Long followedId) {
        return followingJpaRepository.findAllByFollowingId(followedId);
    }
}
