package server.repository.melodyCard;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.album.Album;
import server.domain.melodyCard.MelodyCard;

import java.util.List;
import java.util.Optional;

public interface MelodyCardJpaRepository extends JpaRepository<MelodyCard, Long> {

    Optional<MelodyCard> findById(final long melodyCardId);

    Optional<MelodyCard> findByAlbumId(final long albumId);

    List<MelodyCard> findAllByAlbumId(final long albumId);
}
