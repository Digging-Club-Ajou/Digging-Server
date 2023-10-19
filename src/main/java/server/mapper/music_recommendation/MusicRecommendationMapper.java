package server.mapper.music_recommendation;

import server.domain.music_recommentdation.MusicRecommendation;

public class MusicRecommendationMapper {

    private MusicRecommendationMapper() {
    }

    public static MusicRecommendation toEntity(final long memberId, final String artistName) {
        return MusicRecommendation.builder()
                .memberId(memberId)
                .artistName(artistName)
                .build();
    }
}
