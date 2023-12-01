package server.mapper.music_recommendation.dto;

import java.util.List;
import java.util.Map;

public record AIRecommendationIdMapper(
        Map<Long, List<Long>> memberIds
) {
}
