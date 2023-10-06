package server.mapper.album;

import server.domain.profile.Album;
import server.mapper.album.dto.AlbumValidateResponse;
import server.mapper.album.dto.AlbumImageUrlResponse;

public class AlbumMapper {

    private AlbumMapper() {
    }

    public static Album toEntity(final long memberId, final String albumName) {
        return Album.builder()
                .memberId(memberId)
                .albumName(albumName)
                .build();
    }

    public static AlbumValidateResponse toAlbumValidateResponse(final boolean alreadyExist) {
        return new AlbumValidateResponse(alreadyExist);
    }

    public static AlbumImageUrlResponse toAlbumImageUrlResponse(final String imageUrl) {
        return new AlbumImageUrlResponse(imageUrl);
    }
}
