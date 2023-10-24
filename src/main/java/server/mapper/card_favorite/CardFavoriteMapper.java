package server.mapper.card_favorite;

import server.domain.card_favorite.CardFavorite;

public class CardFavoriteMapper {

    private CardFavoriteMapper() {
    }

    public static CardFavorite toEntity(final long memberId, final long melodyCardId) {
        return CardFavorite.builder()
                .memberId(memberId)
                .melodyCardId(melodyCardId)
                .build();
    }
}
