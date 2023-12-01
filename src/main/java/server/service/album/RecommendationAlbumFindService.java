package server.service.album;

import server.mapper.album.dto.AlbumResponse;

import java.util.List;

public interface RecommendationAlbumFindService {

    List<AlbumResponse> findAll(final long memberId);
}
