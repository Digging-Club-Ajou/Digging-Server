package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.album.dto.AlbumResponse;
import server.service.ai.AIService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationAlbumFindService {

    private final AlbumFindService albumFindService;
    private final AIService aiService;

    public RecommendationAlbumFindService(final AlbumFindService albumFindService, final AIService aiService) {
        this.albumFindService = albumFindService;
        this.aiService = aiService;
    }

    // todo ML 로직 확정되면 변경 (임시 코드)
//    @Transactional(readOnly = true)
//    public List<AlbumResponse> findAll(final long memberId) {
//        AIRecommendationIdResult aiRecommendationResult = aiService.findIds(memberId);
//        List<Long> memberIds = aiRecommendationResult.memberIds();
//        List<AlbumResponse> albumResponses = new ArrayList<>();
//
//        for (long id : memberIds) {
//            Optional<Album> optionalAlbum = albumFindService.findByMemberId(id);
//            if (optionalAlbum.isPresent()) {
//                Album album = optionalAlbum.get();
//                AlbumResponse albumResponse = albumFindService.getAlbumResponse(album.getId());
//                albumResponses.add(albumResponse);
//            }
//        }
//
//        return albumResponses;
//    }

    // todo ML 로직 확정되면 변경 (임시 코드)
    @Transactional(readOnly = true)
    public List<AlbumResponse> findAll(final long memberId) {
        List<AlbumResponse> albumResponses = new ArrayList<>();
        int i = 0;

        while(albumResponses.size() < 5) {
            AlbumResponse albumResponse = albumFindService.getAlbumResponse(i++);
            albumResponses.add(albumResponse);
        }

        return albumResponses;
    }
}
