package server.repository.card_favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.card_favorite.CardFavorite;

public interface CardFavoriteJpaRepository extends JpaRepository<CardFavorite, Long> {
}
