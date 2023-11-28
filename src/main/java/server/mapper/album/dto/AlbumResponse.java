package server.mapper.album.dto;

import lombok.Builder;
import server.domain.album.Album;

import java.util.List;

@Builder
public record AlbumResponse(
        long memberId,
        long albumId,
        String nickname,
        String albumName,
        String imageUrl,
        List<String> artistNames
) {

    public static AlbumResponse toAlbumResponse(final Album album, final String imageUrl,
                                                final List<String> artistNames) {
        return AlbumResponse.builder()
                .memberId(album.getMemberId())
                .albumId(album.getId())
                .nickname(album.getNickname())
                .albumName(album.getAlbumName())
                .imageUrl(imageUrl)
                .artistNames(artistNames)
                .build();
    }

    public static AlbumResponse toAlbumResponseTest(final Album album, final String imageUrl,
                                                    final List<String> artistNames) {
        return AlbumResponse.builder()
                .memberId(album.getMemberId())
                .albumId(album.getMemberId())
                .nickname(album.getNickname())
                .albumName(album.getAlbumName())
                .imageUrl(imageUrl)
                .artistNames(artistNames)
                .build();
    }
}
