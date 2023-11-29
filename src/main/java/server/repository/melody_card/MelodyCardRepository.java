package server.repository.melody_card;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.domain.melody_card.MelodyCard;
import server.global.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static server.domain.melody_card.QMelodyCard.*;
import static server.global.constant.ExceptionMessage.MELODY_CARD_NOT_FOUND;

@Repository
public class MelodyCardRepository {

    private final MelodyCardJpaRepository melodyCardJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MelodyCardRepository(MelodyCardJpaRepository melodyCardJpaRepository, final JPAQueryFactory queryFactory){
        this.melodyCardJpaRepository = melodyCardJpaRepository;
        this.queryFactory = queryFactory;
    }

    public Optional<MelodyCard> findByAlbumId(Long albumId){return melodyCardJpaRepository.findByAlbumId(albumId);}

    public Optional<MelodyCard> findByMemberId(Long albumId){return melodyCardJpaRepository.findByAlbumId(albumId);}

    public MelodyCard save(final MelodyCard melodyCard){
        return melodyCardJpaRepository.save(melodyCard);
    }

    public Optional<MelodyCard> findById(final long melodyCardId) {
        return melodyCardJpaRepository.findById(melodyCardId);
    }

    public MelodyCard getById(final long melodyCardId) {
        return melodyCardJpaRepository.findById(melodyCardId)
                .orElseThrow(() -> new NotFoundException(MELODY_CARD_NOT_FOUND.message));
    }

    public List<MelodyCard> findAllByAlbumId(final long albumId) {
        return melodyCardJpaRepository.findAllByAlbumIdOrderByIdDesc(albumId);
    }

    public List<MelodyCard> findAllByMemberId(final long memberId) {
        return melodyCardJpaRepository.findAllByMemberIdOrderByIdDesc(memberId);
    }

    public List<String> findAllArtistName(final long albumId) {
        return queryFactory.select(melodyCard.artistName)
                .from(melodyCard)
                .where(melodyCard.albumId.eq(albumId))
                .fetch();
    }

    public void delete(final MelodyCard melodyCard) {
        melodyCardJpaRepository.delete(melodyCard);
    }

    public void deleteById(final long melodyCardId){
        melodyCardJpaRepository.deleteById(melodyCardId);
    }
}
