package server.mapper.spotify;

// Spotify API를 이용해서 Snake Case 사용해야함
public record SpotifyTokenInfo(
        String access_token,
        String token_type,
        String expires_in
) {
}
