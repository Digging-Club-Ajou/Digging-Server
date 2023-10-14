package server.mapper.melodyCard;

import server.domain.melodyCard.MelodyCard;
import server.domain.member.vo.MemberSession;
import server.mapper.melodyCard.dto.MelodyCardImageUrlResponse;
import server.mapper.melodyCard.dto.MelodyCardImageUrlResponses;
import server.mapper.melodyCard.dto.MelodyCardRequest;
import server.mapper.melodyCard.dto.MelodyCardResponse;

import java.util.List;

public class MelodyCardMapper {


    private MelodyCardMapper() {
    }
    public static MelodyCard toEntity(final long albumId, final MelodyCardRequest melodyCardRequest) {

        return MelodyCard.builder()
                .albumId(albumId)
                .singer(melodyCardRequest.singer())
                .songTitle(melodyCardRequest.songTitle())
                .genre(melodyCardRequest.genre())
                .streamingLink(melodyCardRequest.streamingLink())
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
                .singer(melodyCard.getSinger())
                .songTitle(melodyCard.getSongTitle())
                .streamingLink(melodyCard.getStreamingLink())
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
