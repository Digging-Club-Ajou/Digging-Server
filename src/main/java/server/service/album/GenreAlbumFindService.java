package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.album.dto.AlbumResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreAlbumFindService {

    private final AlbumFindService albumFindService;

    public GenreAlbumFindService(final AlbumFindService albumFindService) {
        this.albumFindService = albumFindService;
    }

    // todo 장르 확정되면 변경 (임시 코드)
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
