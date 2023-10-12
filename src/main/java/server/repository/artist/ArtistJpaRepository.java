package server.repository.artist;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.artist.Artist;

public interface ArtistJpaRepository extends JpaRepository<Artist, Long> {
}
