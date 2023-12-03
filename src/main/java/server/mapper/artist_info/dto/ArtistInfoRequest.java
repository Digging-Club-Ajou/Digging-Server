package server.mapper.artist_info.dto;

import lombok.Builder;

@Builder
public record ArtistInfoRequest(
        String artistName
) {
}
