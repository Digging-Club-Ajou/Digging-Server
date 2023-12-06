package server.service.artist_info;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.artist_info.ArtistInfo;
import server.mapper.artist_info.ArtistInfoMapper;
import server.mapper.artist_info.dto.ArtistInfoResponse;
import server.mapper.artist_info.dto.ArtistInfoResponses;
import server.mapper.artist_info.dto.ArtistInfoRequest;
import server.mapper.artist_info.dto.ArtistInfoRequests;
import server.repository.artist_info.ArtistInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistInfoService {
    private final ArtistInfoRepository artistInfoRepository;

    public ArtistInfoService(final ArtistInfoRepository artistInfoRepository) {
        this.artistInfoRepository = artistInfoRepository;
    }



    @Transactional
    public ArtistInfoRequests getNullArtists() {
        List<ArtistInfoRequest> artistInfoRequests = new ArrayList<>();
        List<String> artists = artistInfoRepository.findGenresAllNullArtist();
        artists.forEach( artistName ->{
                    ArtistInfoRequest artistInfoRequest = ArtistInfoRequest.builder().artistName(artistName).build();
                    artistInfoRequests.add(artistInfoRequest);
                }

        );
        return new ArtistInfoRequests(artistInfoRequests);

    }


    @Transactional
    public void createArtistInfo(ArtistInfoResponses artistInfoResponses) {
        for (ArtistInfoResponse artistInfoResponse : artistInfoResponses.artistInfoResponses()) {
            Optional<ArtistInfo> optionalArtistInfo = artistInfoRepository.findArtistInfoByArtistName(artistInfoResponse.artistName());

            if(optionalArtistInfo.isPresent()){
                ArtistInfo artistInfo = optionalArtistInfo.get();
                artistInfo.updateArtistInfo(artistInfoResponse);
            }else{
                ArtistInfo artistInfo = ArtistInfoMapper.toEntity(artistInfoResponse);
                artistInfoRepository.save(artistInfo);
            }

        }
    }



}
