package server.service.play_record;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.play_record.dto.PlayRecordResponse;
import server.repository.card_favorite.CardFavoriteRepository;
import server.repository.music_recommendation.MusicRecommendationRepository;
import server.repository.play_record.PlayRecordRepository;
import server.service.spotify.SpotifySearchMusicService;

import java.util.Optional;

@Service
public class PlayRecordFindService {

    private final MusicRecommendationRepository musicRecommendationRepository;
    private final CardFavoriteRepository cardFavoriteRepository;
    private final PlayRecordRepository playRecordRepository;
    private final SpotifySearchMusicService spotifySearchMusicService;

    public PlayRecordFindService(final MusicRecommendationRepository musicRecommendationRepository,
                                 final CardFavoriteRepository cardFavoriteRepository,
                                 final PlayRecordRepository playRecordRepository,
                                 final SpotifySearchMusicService spotifySearchMusicService) {
        this.musicRecommendationRepository = musicRecommendationRepository;
        this.cardFavoriteRepository = cardFavoriteRepository;
        this.playRecordRepository = playRecordRepository;
        this.spotifySearchMusicService = spotifySearchMusicService;
    }

    @Transactional(readOnly = true)
    public PlayRecordResponse findPlayRecord(final long memberId) {
        Optional<String> mostPlayedArtistNameFromRecord = playRecordRepository.findMostPlayedArtistName(memberId);
        Optional<String> favoriteArtistNameFromRecord = cardFavoriteRepository.findFavoriteArtistName(memberId);
        Optional<String> favoriteSongTileFromRecord = playRecordRepository.findFavoriteSongTile(memberId);
        String favoriteGenre = findFavoriteGenre(memberId, mostPlayedArtistNameFromRecord);

        return getPlayRecordResponse(mostPlayedArtistNameFromRecord, favoriteArtistNameFromRecord,
                favoriteSongTileFromRecord, favoriteGenre);
    }

    private String findFavoriteGenre(final long memberId, final Optional<String> favoriteArtistNameFromRecord) {
        if (favoriteArtistNameFromRecord.isPresent()) {
            return spotifySearchMusicService.findGenre(favoriteArtistNameFromRecord.get());
        }

        Optional<String> favoriteArtistNameFromGenre = musicRecommendationRepository.findFavoriteArtistName(memberId);
        return favoriteArtistNameFromGenre.map(spotifySearchMusicService::findGenre).orElse(null);
    }

    private PlayRecordResponse getPlayRecordResponse(final Optional<String> mostPlayedArtistNameFromRecord,
                                                     final Optional<String> favoriteArtistNameFromRecord,
                                                     final Optional<String> favoriteSongTileFromRecord,
                                                     final String favoriteGenre) {

        PlayRecordResponse playRecordResponse = new PlayRecordResponse();
        mostPlayedArtistNameFromRecord.ifPresent(playRecordResponse::addMostPlayedArtistName);
        favoriteArtistNameFromRecord.ifPresent(playRecordResponse::addFavoriteArtistName);
        favoriteSongTileFromRecord.ifPresent(playRecordResponse::addSongTitle);
        playRecordResponse.addGenre(favoriteGenre);
        playRecordResponse.validate();

        return playRecordResponse;
    }
}
