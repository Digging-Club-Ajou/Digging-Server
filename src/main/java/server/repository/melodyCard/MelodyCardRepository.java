package server.repository.melodyCard;


import org.springframework.stereotype.Repository;
import server.domain.melodyCard.MelodyCard;
import server.global.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static server.global.constant.ExceptionMessage.MELODY_CARD_NOT_FOUND;

@Repository
public class MelodyCardRepository {

    private final MelodyCardJpaRepository melodyCardJpaRepository;

    public MelodyCardRepository(MelodyCardJpaRepository melodyCardJpaRepository){
        this.melodyCardJpaRepository = melodyCardJpaRepository;
    }

    public Optional<MelodyCard> findByAlbumId(Long albumId){return melodyCardJpaRepository.findByAlbumId(albumId);}

    public MelodyCard save(final MelodyCard melodyCard){
        return melodyCardJpaRepository.save(melodyCard);
    }

    public MelodyCard getById(final long melodyCardId) {
        return melodyCardJpaRepository.findById(melodyCardId)
                .orElseThrow(() -> new NotFoundException(MELODY_CARD_NOT_FOUND.message));
    }

    public List<MelodyCard> findAllByAlbumId(final long albumId) {
        return melodyCardJpaRepository.findAllByAlbumId(albumId);
    }
}
