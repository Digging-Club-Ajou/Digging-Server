package server.service.artist_info;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.artist_info.ArtistInfo;
import server.global.exception.NotFoundException;
import server.mapper.artist_info.ArtistInfoMapper;
import server.mapper.artist_info.dto.ArtistInfoRequest;
import server.mapper.artist_info.dto.ArtistInfoRequests;
import server.repository.artist_info.ArtistInfoRepository;

@Service
public class ArtistInfoService {
    private final ArtistInfoRepository artistInfoRepository;

    public ArtistInfoService(final ArtistInfoRepository artistInfoRepository) {
        this.artistInfoRepository = artistInfoRepository;
    }


    @Transactional
    public void getNullArtists() {

    }


    @Transactional
    public void createArtistInfo(ArtistInfoRequests artistInfoRequests) {
        for (ArtistInfoRequest artistInfoRequest : artistInfoRequests.artistInfoRequests()) {
            try {
                ArtistInfo artistInfo = artistInfoRepository.findArtistInfoByArtistName(artistInfoRequest.artistName());
                artistInfo.updateArtistInfo(artistInfoRequest);
            }catch (NotFoundException e){
                ArtistInfo artistInfo = ArtistInfoMapper.toEntity(artistInfoRequest);
                artistInfoRepository.save(artistInfo);
            }
        }
    }



}
