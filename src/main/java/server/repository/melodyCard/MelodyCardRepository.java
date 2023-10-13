package server.repository.melodyCard;


import org.springframework.stereotype.Repository;
import server.domain.melodyCard.MelodyCard;

import java.util.Optional;

@Repository
public class MelodyCardRepository {

    private final MelodyCardJpaRepository melodyCardJpaRepository;

    public MelodyCardRepository(MelodyCardJpaRepository melodyCardJpaRepository){
        this.melodyCardJpaRepository = melodyCardJpaRepository;
    }

    public Optional<MelodyCard> findByAlbumId(Long albumId){return melodyCardJpaRepository.findByAlbumId(albumId);}
    public void save (MelodyCard melodyCard){
//        System.out.println("melody card is save successfully" + melodyCard);
        melodyCardJpaRepository.save(melodyCard);
    }

}
