package server.service.following;

import org.springframework.stereotype.Service;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.mapper.album.dto.AlbumResponse;
import server.service.album.AlbumFindService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowingAlbumFindService {

    private final FollowingFindService followingFindService;
    private final AlbumFindService albumFindService;

    public FollowingAlbumFindService(final FollowingFindService followingFindService,
                                     final AlbumFindService albumFindService) {
        this.followingFindService = followingFindService;
        this.albumFindService = albumFindService;
    }

    public List<AlbumResponse> findAll(final long memberId) {
        List<FollowingInfo> followingInfos = followingFindService.findAllByFollowingId(memberId);
        List<AlbumResponse> albumResponses = new ArrayList<>();

        for (FollowingInfo followingInfo : followingInfos) {
            long followedId = followingInfo.getFollowedId();
            Optional<Album> optionalAlbum = albumFindService.findByMemberId(followedId);

            if (optionalAlbum.isPresent()) {
                Album album = optionalAlbum.get();
                AlbumResponse albumResponse = albumFindService.getAlbumResponse(album.getId());
                albumResponses.add(albumResponse);
            }
        }

        return albumResponses;
    }
}
