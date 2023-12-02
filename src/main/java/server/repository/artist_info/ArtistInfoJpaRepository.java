package server.repository.artist_info;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.artist_info.ArtistInfo;

import java.util.Optional;

public interface ArtistInfoJpaRepository extends JpaRepository<ArtistInfo, Long> {

    Optional<ArtistInfo> findArtistInfoByArtistName(final String artistName);

//    List<ArtistGenre> findArtistGenresByBalladeIsNullAndDanceIsNullAndRockMetalIsNullAndRbAndSoulIsNullAndRapHiphopIsNullAndFolkBluesIsNullAAndIndieIsNullAAndPopIsNullAndOstAndMusicalIsNull();


}
