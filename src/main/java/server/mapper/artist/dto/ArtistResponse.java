package server.mapper.artist.dto;

import lombok.Builder;
import server.domain.album.Album;
import server.mapper.album.dto.AlbumResponse;

import java.util.List;
@Builder
public record ArtistResponse(
        String name,
        String imageUrl,
        String nation
) {
    public static ArtistResponse toArtistsResponse(final String name, final String imageUrl,
                                                final String nation) {
        return ArtistResponse.builder()
                .name(name)
                .imageUrl(imageUrl)
                .nation(nation)
                .build();
    }
}
