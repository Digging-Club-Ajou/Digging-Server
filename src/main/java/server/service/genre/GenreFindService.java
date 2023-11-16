package server.service.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import server.repository.music_recommendation.MusicRecommendationRepository;
import server.repository.play_record.PlayRecordRepository;
import server.service.spotify.SpotifySearchMusicService;

import java.util.Optional;

@Slf4j
@Service
public class GenreFindService {

    private final MusicRecommendationRepository musicRecommendationRepository;
    private final PlayRecordRepository playRecordRepository;
    private final SpotifySearchMusicService spotifySearchMusicService;

    public GenreFindService(final MusicRecommendationRepository musicRecommendationRepository,
                            final PlayRecordRepository playRecordRepository,
                            final SpotifySearchMusicService spotifySearchMusicService) {
        this.musicRecommendationRepository = musicRecommendationRepository;
        this.playRecordRepository = playRecordRepository;
        this.spotifySearchMusicService = spotifySearchMusicService;
    }

    public String findFavoriteGenre(final long memberId) {
        Optional<String> favoriteArtistNameFromRecord = playRecordRepository.findFavoriteArtistName(memberId);
        if (favoriteArtistNameFromRecord.isPresent()) {
            return spotifySearchMusicService.findGenre(favoriteArtistNameFromRecord.get());
        }

        Optional<String> favoriteArtistNameFromGenre = musicRecommendationRepository.findFavoriteArtistName(memberId);

        if (favoriteArtistNameFromGenre.isPresent()) {
            return spotifySearchMusicService.findGenre(favoriteArtistNameFromGenre.get());
        }

        return "아직 활동을 추적할 수 없습니다";
    }
}
