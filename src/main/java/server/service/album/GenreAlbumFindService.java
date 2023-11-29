package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.album.dto.AlbumResponse;
import server.mapper.music_recommendation.dto.AIRecommendationGenreResult;
import server.mapper.music_recommendation.dto.AIRecommendationMelodyCard;
import server.mapper.spotify.SpotifySearchDto;
import server.service.ai.AIService;
import server.service.spotify.SpotifySearchMusicService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreAlbumFindService {

    private final SpotifySearchMusicService spotifySearchMusicService;
    private final AlbumFindService albumFindService;
    private final AIService aiService;

    public GenreAlbumFindService(final SpotifySearchMusicService spotifySearchMusicService,
                                 final AlbumFindService albumFindService,
                                 final AIService aiService) {
        this.spotifySearchMusicService = spotifySearchMusicService;
        this.albumFindService = albumFindService;
        this.aiService = aiService;
    }

    // todo 장르 확정되면 변경 (임시 코드)
//    public List<AlbumResponse> findAll(final long memberId) {
//        AIRecommendationGenreResult aiRecommendationGenreResult = aiService.findAlbumAndMelodyCard(memberId);
//        List<AIRecommendationMelodyCard> aiRecommendationMelodyCards = aiRecommendationGenreResult.melodyCards();
//
//        List<AlbumResponse> albumResponses = new ArrayList<>();
//        String albumName = aiRecommendationGenreResult.albumName();
//
//        for (AIRecommendationMelodyCard aiRecommendationMelodyCard : aiRecommendationMelodyCards) {
//            String artistName = aiRecommendationMelodyCard.artistName();
//            String songTitle = aiRecommendationMelodyCard.songTitle();
//
//            List<SpotifySearchDto> spotifySearchDtos =
//                    spotifySearchMusicService.searchTracks(artistName + " " + songTitle, 1);
//
//            SpotifySearchDto spotifySearchDto = spotifySearchDtos.get(0);
//
//        }
//
//        for (int i = 1; i < 4; i++) {
//            AlbumResponse albumResponse = albumFindService.getAlbumResponse(i);
//            albumResponses.add(albumResponse);
//        }
//
//        return albumResponses;
//    }

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
