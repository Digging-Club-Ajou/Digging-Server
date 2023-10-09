package server.mapper.spotify;

import java.util.List;

public record SpotifyMusicResult(
        List<SpotifySearchDto> spotifySearchDtos
) {
}
