package server.mapper.profile;

import server.domain.profile.Album;

public class AlbumMapper {

    private AlbumMapper() {
    }

    public static Album toEntity(final long memberId, final String albumName) {
        return Album.builder()
                .memberId(memberId)
                .albumName(albumName)
                .build();
    }
}
