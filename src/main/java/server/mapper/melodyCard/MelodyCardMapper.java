package server.mapper.melodyCard;

import server.domain.album.Album;
import server.domain.genre.Genre;
import server.domain.melodyCard.MelodyCard;
import server.domain.member.vo.MemberSession;
import server.mapper.album.dto.AlbumImageUrlResponse;
import server.mapper.genre.dto.GenreRequest;
import server.mapper.melodyCard.dto.MelodyCardImageUrlResponse;
import server.mapper.melodyCard.dto.MelodyCardRequest;

public class MelodyCardMapper {


    private MelodyCardMapper() {
    }
    public static MelodyCard toEntity(final Album album, final MelodyCardRequest melodyCardRequest) {


        return MelodyCard.builder()
                .albumId(album.getId())
                .singer(melodyCardRequest.singer())
                .songTitle(melodyCardRequest.songTitle())
                .genre(melodyCardRequest.genre())
                .streamingLink(melodyCardRequest.streamingLink())
                .address(melodyCardRequest.address())
                .cardDescription(melodyCardRequest.cardDescription())
                .color(melodyCardRequest.color())
                .build();
    }

    public static MelodyCardImageUrlResponse toMelodyCardImageUrlResponse(final String imageUrl) {
        return new MelodyCardImageUrlResponse(imageUrl);
    }

}
