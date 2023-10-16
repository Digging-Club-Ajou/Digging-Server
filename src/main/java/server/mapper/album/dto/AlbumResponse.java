package server.mapper.album.dto;

import lombok.Builder;
import server.domain.album.Album;

@Builder
public record AlbumResponse(
        long memberId,
        long albumId,
        String nickname,
        String albumName,
        String imageUrl
) {

    public static AlbumResponse toAlbumResponse(final Album album, final String imageUrl) {
        return AlbumResponse.builder()
                .memberId(album.getMemberId())
                .albumId(album.getId())
                .nickname(album.getNickname())
                .albumName(album.getAlbumName())
                .imageUrl(imageUrl)
                .build();
    }
}
