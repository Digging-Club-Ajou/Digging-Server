package server.mapper.album;

import server.domain.album.Album;
import server.domain.member.vo.MemberSession;
import server.mapper.album.dto.AlbumValidateResponse;
import server.mapper.album.dto.AlbumImageUrlResponse;

public class AlbumMapper {

    private AlbumMapper() {
    }

    public static Album toEntity(final MemberSession memberSession, final String albumName) {
        return Album.builder()
                .memberId(memberSession.id())
                .nickname(memberSession.nickname())
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
