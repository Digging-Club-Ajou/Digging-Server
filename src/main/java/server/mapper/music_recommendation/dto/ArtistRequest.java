package server.mapper.music_recommendation.dto;

import java.util.List;

public record ArtistRequest(
        List<String> artistNames
) {
}
