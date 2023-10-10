package server.mapper.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotifyTokenInfo(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("expires_in")
        String expiresIn
) {
}
