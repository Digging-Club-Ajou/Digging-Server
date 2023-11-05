package server.service.album;

import org.springframework.stereotype.Service;
import server.mapper.album.dto.AlbumResponse;

@Service
public interface AlbumFindService {

    AlbumResponse getAlbumResponse(final long albumId);

    AlbumResponse getLoginMemberAlbumResponse(final long memberId);
}
