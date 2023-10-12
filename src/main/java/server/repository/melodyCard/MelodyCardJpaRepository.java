package server.repository.melodyCard;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.album.Album;
import server.domain.melodyCard.MelodyCard;

import java.util.Optional;

public interface MelodyCardJpaRepository extends JpaRepository<MelodyCard, Long> {


    public Optional<MelodyCard> findByAlbumId(Long albumId);
}
