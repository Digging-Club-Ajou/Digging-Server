package server.mapper.artist;

import server.domain.artist.Artist;

public class ArtistMapper {

    private ArtistMapper() {
    }

    public static Artist toEntity(final long memberId, final String artistName) {
        return Artist.builder()
                .memberId(memberId)
                .artistName(artistName)
                .build();
    }
}
