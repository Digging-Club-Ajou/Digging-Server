package server.repository.melody_card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.domain.melody_card.MelodyCard;

import java.util.List;
import java.util.Optional;

public interface MelodyCardJpaRepository extends JpaRepository<MelodyCard, Long> {

    Optional<MelodyCard> findById(final long melodyCardId);

    Optional<MelodyCard> findByAlbumId(final long albumId);

    List<MelodyCard> findAllByAlbumId(final long albumId);

    List<MelodyCard> findAllByMemberIdOrderByIdDesc(final long memberId);

}
