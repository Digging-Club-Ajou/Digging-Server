package server.repository.music_recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.music_recommentdation.MusicRecommendation;

import java.util.List;

public interface MusicRecommendationJpaRepository extends JpaRepository<MusicRecommendation, Long> {
}
