package server.repository.artist;

import org.springframework.stereotype.Repository;
import server.domain.artist.ArtistInfo;

import java.util.List;

@Repository
public class ArtistInfoRepository {

    private ArtistInfoJpaRepository artistInfoJpaRepository;

    public ArtistInfoRepository(ArtistInfoJpaRepository artistInfoJpaRepository){
        this.artistInfoJpaRepository = artistInfoJpaRepository;
    }

    public void saveAll(final List<ArtistInfo> artistInfoList){artistInfoJpaRepository.saveAll(artistInfoList);}
    public void save(final ArtistInfo artistInfo){artistInfoJpaRepository.save(artistInfo);}

    public List<ArtistInfo> findAllBy(){return artistInfoJpaRepository.findAllBy();}
}
