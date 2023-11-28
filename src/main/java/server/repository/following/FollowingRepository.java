package server.repository.following;

import org.springframework.stereotype.Repository;
import server.domain.following.FollowingInfo;

import java.util.List;

@Repository
public class FollowingRepository {

    private final FollowingJpaRepository followingJpaRepository;

    public FollowingRepository(final FollowingJpaRepository followingJpaRepository){
        this.followingJpaRepository = followingJpaRepository;
    }

    public void save(final FollowingInfo followingInfo){
        followingJpaRepository.save(followingInfo);
    }

    public void delete(final FollowingInfo followingInfo){
        followingJpaRepository.delete(followingInfo);
    }

    public List<FollowingInfo> findAllByFollowedId(final long followedId) {
        return followingJpaRepository.findAllByFollowedId(followedId);
    }

    public List<FollowingInfo> findAllByFollowingId(final long followingId) {
        return followingJpaRepository.findAllByFollowingIdOrderByLastModifiedAtDesc(followingId);
    }

    public FollowingInfo findByFollowingIdAndFollowedId(final long followingId, final long followedId){
         return followingJpaRepository.findByFollowingIdAndAndFollowedId(followingId,followedId);
    }

}
