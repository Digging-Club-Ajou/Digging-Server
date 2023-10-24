package server.repository.card_favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.card_favorite.CardFavorite;

import java.util.List;
import java.util.Optional;

public interface CardFavoriteJpaRepository extends JpaRepository<CardFavorite, Long> {

    Optional<CardFavorite> findByMemberIdAndMelodyCardId(final long memberId, final long melodyCardId);

    List<CardFavorite> findAllByMemberId(final long memberId);
}
