package server.service.following;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.following.FollowingInfo;
import server.mapper.following.dto.FollowingDto;
import server.repository.following.FollowingRepository;

@Service
public class FollowingService {

    private FollowingRepository followingRepository;

    public FollowingService(final FollowingRepository followingRepository){
        this.followingRepository = followingRepository;
    }


    @Transactional
    public void saveFollowing(Long memberId, FollowingDto followingDto){
        FollowingInfo followingInfo = FollowingInfo.builder().followingId(followingDto.followingId()).followedId(memberId).build();
        followingRepository.save(followingInfo);
    }
}
