package server.mapper.melody_card;

import server.domain.melody_card.MelodyCard;
import server.domain.member.vo.MemberSession;
import server.mapper.melody_card.dto.MelodyCardImageUrlResponse;
import server.mapper.melody_card.dto.MelodyCardImageUrlResponses;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;

import java.util.List;

public class MelodyCardMapper {


    private MelodyCardMapper() {
    }
    public static MelodyCard toEntity(final long albumId, final MelodyCardRequest melodyCardRequest) {

        return MelodyCard.builder()
                .albumId(albumId)
                .artistName(melodyCardRequest.artistName())
                .songTitle(melodyCardRequest.songTitle())
                .genre(melodyCardRequest.genre())
                .previewUrl(melodyCardRequest.previewUrl())
                .address(melodyCardRequest.address())
                .cardDescription(melodyCardRequest.cardDescription())
                .color(melodyCardRequest.color())
                .build();
    }

    public static MelodyCardResponse toMelodyCardResponse(final MemberSession memberSession,
                                                          final MelodyCard melodyCard) {
        return MelodyCardResponse.builder()
                .memberId(memberSession.id())
                .nickname(memberSession.nickname())
                .artistName(melodyCard.getArtistName())
                .songTitle(melodyCard.getSongTitle())
                .previewUrl(melodyCard.getPreviewUrl())
                .address(melodyCard.getAddress())
                .cardDescription(melodyCard.getCardDescription())
                .color(melodyCard.getColor())
                .build();
    }

    public static MelodyCardImageUrlResponse toMelodyCardImageUrlResponse(final String imageUrl) {
        return new MelodyCardImageUrlResponse(imageUrl);
    }

    public static MelodyCardImageUrlResponses toMelodyCardImageUrlResponses(final List<String> imageUrls) {
        return new MelodyCardImageUrlResponses(imageUrls);
    }
}
