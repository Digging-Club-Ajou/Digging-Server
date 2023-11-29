package server.repository.card_favorite;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.domain.card_favorite.CardFavorite;

import java.util.List;
import java.util.Optional;

import static server.domain.card_favorite.QCardFavorite.*;
import static server.domain.melody_card.QMelodyCard.melodyCard;

@Repository
public class CardFavoriteRepository {

    private final CardFavoriteJpaRepository cardFavoriteJpaRepository;
    private final JPAQueryFactory queryFactory;

    public CardFavoriteRepository(final CardFavoriteJpaRepository cardFavoriteJpaRepository,
                                  final JPAQueryFactory queryFactory) {
        this.cardFavoriteJpaRepository = cardFavoriteJpaRepository;
        this.queryFactory = queryFactory;
    }

    public void save(final CardFavorite cardFavorite) {
        cardFavoriteJpaRepository.save(cardFavorite);
    }

    public Optional<CardFavorite> findByMemberIdAndMelodyCardId(final long memberId, final long melodyCardId) {
        return cardFavoriteJpaRepository.findByMemberIdAndMelodyCardId(memberId, melodyCardId);
    }

    public List<CardFavorite> findAllByMemberId(final long memberId) {
        return cardFavoriteJpaRepository.findAllByMemberIdOrderByLastModifiedAtDesc(memberId);
    }

    public Optional<String> findFavoriteArtistName(final long memberId) {
        String artistName = queryFactory.select(melodyCard.artistName)
                .from(cardFavorite)
                .where(cardFavorite.memberId.eq(memberId))
                .innerJoin(melodyCard)
                .on(cardFavorite.melodyCardId.eq(melodyCard.id))
                .groupBy(melodyCard.artistName)
                .orderBy(melodyCard.artistName.count().desc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(artistName);
    }

    public void delete(CardFavorite cardFavorite){
        cardFavoriteJpaRepository.delete(cardFavorite);
    }

    public long count() {
        return cardFavoriteJpaRepository.count();
    }
}
