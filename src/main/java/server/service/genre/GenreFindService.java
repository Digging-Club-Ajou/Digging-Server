package server.service.genre;

import org.springframework.stereotype.Service;
import server.repository.music_recommendation.MusicRecommendationRepository;
import server.service.spotify.SpotifySearchMusicService;

import java.util.List;

@Service
public class GenreFindService {

    private final MusicRecommendationRepository musicRecommendationRepository;
    private final SpotifySearchMusicService spotifySearchMusicService;

    public GenreFindService(final MusicRecommendationRepository musicRecommendationRepository,
                            final SpotifySearchMusicService spotifySearchMusicService) {
        this.musicRecommendationRepository = musicRecommendationRepository;
        this.spotifySearchMusicService = spotifySearchMusicService;
    }

    public List<String> findFavoriteGenre(final long memberId) {
        String favoriteArtistName = musicRecommendationRepository.findFavoriteArtistName(memberId);
        return spotifySearchMusicService.findGenre(favoriteArtistName);
    }
}
