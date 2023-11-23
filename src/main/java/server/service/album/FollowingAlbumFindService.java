package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.global.exception.BadRequestException;
import server.mapper.album.dto.AlbumResponse;
import server.repository.album.AlbumRepository;
import server.repository.following.FollowingRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class FollowingAlbumFindService {

    private final FollowingRepository followingRepository;
    private final AlbumRepository albumRepository;
    private final AlbumFindService albumFindService;

    public FollowingAlbumFindService(final FollowingRepository followingRepository,
                                     final AlbumRepository albumRepository,
                                     final AlbumFindService albumFindService) {
        this.followingRepository = followingRepository;
        this.albumRepository = albumRepository;
        this.albumFindService = albumFindService;
    }

    @Transactional(readOnly = true)
    public List<AlbumResponse> findAll(final long memberId) {
        List<FollowingInfo> followingInfos = followingRepository.findAllByFollowingId(memberId);
        List<AlbumResponse> albumResponses = new ArrayList<>();

        for (FollowingInfo followingInfo : followingInfos) {
            try {
                long followedId = followingInfo.getFollowedId();
                Album album = albumRepository.getByMemberId(followedId);
                AlbumResponse albumResponse = albumFindService.getAlbumResponse(album.getId());
                albumResponses.add(albumResponse);
            }catch (BadRequestException e){

            }

        }

        return albumResponses;
    }
}
