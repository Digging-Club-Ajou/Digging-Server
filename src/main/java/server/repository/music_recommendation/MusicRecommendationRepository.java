package server.repository.music_recommendation;

import org.springframework.stereotype.Repository;
import server.domain.music_recommentdation.MusicRecommendation;

@Repository
public class MusicRecommendationRepository {

    private final MusicRecommendationJpaRepository musicRecommendationJpaRepository;

    public MusicRecommendationRepository(final MusicRecommendationJpaRepository musicRecommendationJpaRepository) {
        this.musicRecommendationJpaRepository = musicRecommendationJpaRepository;
    }

    public void save(final MusicRecommendation musicRecommendation) {
        musicRecommendationJpaRepository.save(musicRecommendation);
    }
}
