package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.album.dto.AlbumResponse;
import server.repository.album.AlbumRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationAlbumFindService {

    private final AlbumRepository albumRepository;

    private final AlbumFindService albumFindService;

    public RecommendationAlbumFindService(final AlbumRepository albumRepository,
                                          final AlbumFindService albumFindService) {
        this.albumRepository = albumRepository;
        this.albumFindService = albumFindService;
    }

    // todo ML 로직 확정되면 변경 (임시 코드)
    @Transactional(readOnly = true)
    public List<AlbumResponse> findAll() {
        List<AlbumResponse> albumResponses = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            AlbumResponse albumResponse = albumFindService.getAlbumResponse(i);
            albumResponses.add(albumResponse);
        }

        return albumResponses;
    }
}
