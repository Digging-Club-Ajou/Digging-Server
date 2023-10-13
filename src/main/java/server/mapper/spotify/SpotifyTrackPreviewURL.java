package server.mapper.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpotifyTrackPreviewURL(
        String previewUrl
) {
}
