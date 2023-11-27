package server.service.album;

import org.springframework.stereotype.Service;
import server.domain.album.Album;
import server.mapper.album.dto.AlbumResponse;

import java.util.Optional;

@Service
public interface AlbumFindService {

    Optional<Album> findByMemberId(final long memberId);

    AlbumResponse getAlbumResponse(final long albumId);

    AlbumResponse getLoginMemberAlbumResponse(final long memberId);
}
