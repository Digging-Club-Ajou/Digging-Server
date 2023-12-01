package server.mapper.music_recommendation.dto;

import java.util.List;

public record AIRecommendationResult(
        List<Long> memberIds
) {
}
