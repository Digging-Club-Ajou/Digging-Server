package server.repository.following;

import org.springframework.stereotype.Repository;
import server.domain.following.FollowingInfo;
import server.domain.genre.Genre;
import server.domain.member.persist.Member;

import java.util.List;
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

    public void delete(final FollowingInfo followingInfo){
        followingJpaRepository.delete(followingInfo);
    }

    public List<FollowingInfo> findAllByFollowedId(final Long followedId) {
        return followingJpaRepository.findAllByFollowedId(followedId);
    }

    public List<FollowingInfo> findAllByFollowingId(final Long followingId) {
        return followingJpaRepository.findAllByFollowingId(followingId);
    }


    public FollowingInfo findByFollowingIdAndFollowedId(final Long followingId, final Long followedId){
        return followingJpaRepository.findByFollowingIdAndAndFollowedId(followingId,followedId);
    }

}
