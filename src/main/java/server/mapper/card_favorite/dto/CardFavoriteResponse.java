package server.mapper.card_favorite.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record CardFavoriteResponse(
        long melodyCardId,
        String songTitle,
        String artistName,
        String imageUrl
) {
}
