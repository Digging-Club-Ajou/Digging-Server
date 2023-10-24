package server.repository.card_favorite;

import org.springframework.stereotype.Repository;
import server.domain.card_favorite.CardFavorite;

import java.util.List;
import java.util.Optional;

@Repository
public class CardFavoriteRepository {

    private final CardFavoriteJpaRepository cardFavoriteJpaRepository;

    public CardFavoriteRepository(final CardFavoriteJpaRepository cardFavoriteJpaRepository) {
        this.cardFavoriteJpaRepository = cardFavoriteJpaRepository;
    }

    public void save(final CardFavorite cardFavorite) {
        cardFavoriteJpaRepository.save(cardFavorite);
    }

    public Optional<CardFavorite> findByMemberIdAndMelodyCardId(final long memberId, final long melodyCardId) {
        return cardFavoriteJpaRepository.findByMemberIdAndMelodyCardId(memberId, melodyCardId);
    }

    public List<CardFavorite> findAllByMemberId(final long memberId) {
        return cardFavoriteJpaRepository.findAllByMemberId(memberId);
    }
}
