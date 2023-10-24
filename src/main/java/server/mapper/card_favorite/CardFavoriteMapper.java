package server.mapper.card_favorite;

import server.domain.card_favorite.CardFavorite;
import server.mapper.card_favorite.dto.CardFavoriteRequest;
import server.mapper.card_favorite.dto.CardFavoriteResponse;
import server.mapper.melody_card.dto.MelodyCardResponse;


public class CardFavoriteMapper {

    private CardFavoriteMapper() {
    }

    public static CardFavorite toEntity(final long memberId, final CardFavoriteRequest cardFavoriteRequest) {
        return CardFavorite.builder()
                .memberId(memberId)
                .melodyCardId(cardFavoriteRequest.melodyCardId())
                .isLikes(cardFavoriteRequest.isLikes())
                .build();
    }

    public static CardFavoriteResponse toCardFavoriteResponse(final MelodyCardResponse melodyCardResponse) {
        return CardFavoriteResponse.builder()
                .melodyCardId(melodyCardResponse.getMelodyCardId())
                .songTitle(melodyCardResponse.getSongTitle())
                .artistName(melodyCardResponse.getArtistName())
                .imageUrl(melodyCardResponse.getImageUrl())
                .build();
    }
}
