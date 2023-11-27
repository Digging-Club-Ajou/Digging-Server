package server.service.following;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.following.FollowingInfo;
import server.repository.following.FollowingRepository;

import java.util.List;

@Service
public class FollowingFindService {

    private final FollowingRepository followingRepository;

    public FollowingFindService(final FollowingRepository followingRepository) {
        this.followingRepository = followingRepository;
    }

    @Transactional(readOnly = true)
    public List<FollowingInfo> findAllByFollowingId(final long followingId) {
        return followingRepository.findAllByFollowingId(followingId);
    }
}
