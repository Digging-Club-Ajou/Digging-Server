package server.service.music_recommendation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.music_recommendation.MusicRecommendationMapper;
import server.mapper.music_recommendation.dto.ArtistRequest;
import server.repository.music_recommendation.MusicRecommendationRepository;

import java.util.List;

@Service
public class MusicRecommendationCreateService {

    private final MusicRecommendationRepository musicRecommendationRepository;

    public MusicRecommendationCreateService(final MusicRecommendationRepository musicRecommendationRepository) {
        this.musicRecommendationRepository = musicRecommendationRepository;
    }

    @Transactional
    public void createRecommendation(final long memberId, final ArtistRequest artistRequest) {
        List<String> artistNames = artistRequest.artistNames();
        artistNames.stream()
                .map(
                        artistName -> MusicRecommendationMapper.toEntity(memberId, artistName)
                )
                .forEach(musicRecommendationRepository::save);
    }
}
