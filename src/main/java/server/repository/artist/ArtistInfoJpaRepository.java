package server.repository.artist;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.artist.ArtistInfo;

public interface ArtistInfoJpaRepository extends JpaRepository<ArtistInfo, Long> {
}
