package server.mapper.card_favorite.dto;

public record CardFavoriteRequest(
        long melodyCardId,
        Boolean isLike
) {
}
