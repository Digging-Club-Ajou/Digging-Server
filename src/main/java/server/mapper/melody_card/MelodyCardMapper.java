package server.mapper.melody_card;

import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.melody_card.dto.MelodyCardImageUrlResponse;
import server.mapper.melody_card.dto.MelodyCardImageUrlResponses;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;

import java.util.List;

public class MelodyCardMapper {


    private MelodyCardMapper() {
    }
    public static MelodyCard toEntity(final Album album,
                                      final MelodyCardRequest melodyCardRequest, final Boolean isImageUrl) {

        return MelodyCard.builder()
                .albumId(album.getId())
                .memberId(album.getMemberId())
                .nickname(album.getNickname())
                .artistName(melodyCardRequest.artistName())
                .songTitle(melodyCardRequest.songTitle())
                .genre(melodyCardRequest.genre())
                .previewUrl(melodyCardRequest.previewUrl())
                .address(melodyCardRequest.address())
                .cardDescription(melodyCardRequest.cardDescription())
                .color(melodyCardRequest.color())
                .isImageUrl(isImageUrl)
                .albumCoverImageUrl(melodyCardRequest.albumCoverImageUrl())
                .build();
    }

    public static MelodyCardResponse toMelodyCardResponse(final MelodyCard melodyCard) {
        return MelodyCardResponse.builder()
                .melodyCardId(melodyCard.getId())
                .albumId(melodyCard.getAlbumId())
                .memberId(melodyCard.getMemberId())
                .nickname(melodyCard.getNickname())
                .artistName(melodyCard.getArtistName())
                .songTitle(melodyCard.getSongTitle())
                .previewUrl(melodyCard.getPreviewUrl())
                .address(melodyCard.getAddress())
                .cardDescription(melodyCard.getCardDescription())
                .color(melodyCard.getColor())
                .isImageUrl(melodyCard.getIsImageUrl())
                .albumCoverImageUrl(melodyCard.getAlbumCoverImageUrl())
                .build();
    }

    public static MelodyCardImageUrlResponse toMelodyCardImageUrlResponse(final String imageUrl) {
        return new MelodyCardImageUrlResponse(imageUrl);
    }

    public static MelodyCardImageUrlResponses toMelodyCardImageUrlResponses(final List<String> imageUrls) {
        return new MelodyCardImageUrlResponses(imageUrls);
    }
}
