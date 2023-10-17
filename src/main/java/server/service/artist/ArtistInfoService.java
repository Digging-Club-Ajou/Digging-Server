package server.service.artist;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import server.domain.artist.ArtistInfo;
import server.mapper.artist.dto.ArtistResponse;
import server.mapper.artist.dto.ArtistResponses;
import server.repository.artist.ArtistInfoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistInfoService {

    private ArtistInfoRepository artistInfoRepository;

    public ArtistInfoService(final ArtistInfoRepository artistInfoRepository){
        this.artistInfoRepository = artistInfoRepository;
    }
    @Transactional
    public ArtistResponses getArtistInfos() {

        List<ArtistInfo> artistInfos = artistInfoRepository.findAllBy();
        List<ArtistResponse> artistResponseList = new ArrayList<>();

        artistInfos.forEach(artistInfo -> {
            artistResponseList.add(ArtistResponse.toArtistsResponse(artistInfo.getName(),artistInfo.getImageURL(),artistInfo.getNation()));

        });

        ArtistResponses artistResponses = ArtistResponses.builder().artistResponses(artistResponseList).build();
        return artistResponses;
    }



}
