package server.service.album.test;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.album.dto.AlbumResponse;
import server.service.ai.AIService;
import server.service.album.AlbumFindService;
import server.service.album.AlbumValidationService;
import server.service.album.RecommendationAlbumFindService;

import java.util.ArrayList;
import java.util.List;

// Test Double
@Profile("test")
@Service
public class RecommendationAlbumFindTestService implements RecommendationAlbumFindService {

    private final AlbumFindService albumFindService;
    private final AlbumValidationService albumValidationService;
    private final AIService aiService;

    public RecommendationAlbumFindTestService(final AlbumFindService albumFindService,
                                              final AlbumValidationService albumValidationService,
                                              final AIService aiService) {
        this.albumFindService = albumFindService;
        this.albumValidationService = albumValidationService;
        this.aiService = aiService;
    }

    // todo ML 로직 확정되면 변경 (임시 코드)
    @Transactional(readOnly = true)
    public List<AlbumResponse> findAll(final long memberId) {
        List<AlbumResponse> albumResponses = new ArrayList<>();
        int i = 0;
        int j = 0;

        while(albumResponses.size() < 5 && j < 100) {
            if (albumValidationService.validateExistByAlbumId(i)) {
                AlbumResponse albumResponse = albumFindService.getAlbumResponse(i);
                albumResponses.add(albumResponse);
            }
            i++;
            j++;
        }

        return albumResponses;
    }
}
