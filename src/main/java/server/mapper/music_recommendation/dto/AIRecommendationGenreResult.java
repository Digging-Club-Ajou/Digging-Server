package server.mapper.music_recommendation.dto;

import java.util.List;

public record AIRecommendationGenreResult(
        String albumName,
        List<AIRecommendationMelodyCard> melodyCards
) {
}
