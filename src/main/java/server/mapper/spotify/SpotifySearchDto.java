package server.mapper.spotify;

public record SpotifySearchDto(
        String artistName,
        String songTitle,
        String imageUrl
) {
}
