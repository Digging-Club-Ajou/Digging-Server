package server.repository.artist_info;

import org.springframework.stereotype.Repository;
import server.domain.artist_info.ArtistInfo;
import server.global.exception.NotFoundException;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.ARTIST_INFO_NOT_FOUND;

@Repository
public class ArtistInfoRepository {

    private final ArtistInfoJpaRepository artistInfoJpaRepository;

    public ArtistInfoRepository(final ArtistInfoJpaRepository artistInfoJpaRepository) {
        this.artistInfoJpaRepository = artistInfoJpaRepository;
    }

    public void save(final ArtistInfo artistInfo) {
        artistInfoJpaRepository.save(artistInfo);
    }

    public ArtistInfo findArtistInfoByArtistName(final String artistName) {
        return artistInfoJpaRepository.findArtistInfoByArtistName(artistName)
                .orElseThrow(() -> new NotFoundException(ARTIST_INFO_NOT_FOUND.message));
    }



}
