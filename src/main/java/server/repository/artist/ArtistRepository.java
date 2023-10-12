package server.repository.artist;

import org.springframework.stereotype.Repository;
import server.domain.artist.Artist;

@Repository
public class ArtistRepository {

    private final ArtistJpaRepository artistJpaRepository;

    public ArtistRepository(final ArtistJpaRepository artistJpaRepository) {
        this.artistJpaRepository = artistJpaRepository;
    }

    public void save(final Artist artist) {
        artistJpaRepository.save(artist);
    }
}
