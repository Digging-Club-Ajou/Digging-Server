package server.repository.artist;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.artist.ArtistInfo;


import java.util.List;

public interface ArtistInfoJpaRepository extends JpaRepository<ArtistInfo, Long> {

    List<ArtistInfo> findAllBy();

}
