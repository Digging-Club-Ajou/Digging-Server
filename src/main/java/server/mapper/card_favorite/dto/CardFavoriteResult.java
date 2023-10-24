package server.mapper.card_favorite.dto;

import java.util.List;

public record CardFavoriteResult(
        List<CardFavoriteResponse> cardFavoriteResponses
) {
}
