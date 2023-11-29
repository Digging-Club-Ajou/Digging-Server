package server.mapper.music_recommendation.dto;

import java.util.List;

public record AIRecommendationIdResult(
        List<Long> memberIds
) {
}
