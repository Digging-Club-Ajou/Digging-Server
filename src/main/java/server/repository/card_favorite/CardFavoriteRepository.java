package server.repository.card_favorite;

import org.springframework.stereotype.Repository;
import server.domain.card_favorite.CardFavorite;

@Repository
public class CardFavoriteRepository {

    private final CardFavoriteJpaRepository cardFavoriteJpaRepository;

    public CardFavoriteRepository(final CardFavoriteJpaRepository cardFavoriteJpaRepository) {
        this.cardFavoriteJpaRepository = cardFavoriteJpaRepository;
    }

    public void save(final CardFavorite cardFavorite) {
        cardFavoriteJpaRepository.save(cardFavorite);
    }
}
