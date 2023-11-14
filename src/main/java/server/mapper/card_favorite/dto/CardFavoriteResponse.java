package server.mapper.card_favorite.dto;

import lombok.Builder;

@Builder
public record CardFavoriteResponse(
        long melodyCardId,
        String songTitle,
        String artistName,
        String albumCoverImageUrl
) {
}
