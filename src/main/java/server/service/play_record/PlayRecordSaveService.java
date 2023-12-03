package server.service.play_record;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.artist_info.ArtistInfo;
import server.domain.play_record.PlayRecord;
import server.mapper.play_record.PlayRecordMapper;
import server.mapper.play_record.dto.PlayRecordRequest;
import server.repository.artist_info.ArtistInfoRepository;
import server.repository.play_record.PlayRecordRepository;

@Service
public class PlayRecordSaveService {

    private final PlayRecordRepository playRecordRepository;
    private final ArtistInfoRepository artistInfoRepository;

    public PlayRecordSaveService(final PlayRecordRepository playRecordRepository, final ArtistInfoRepository artistInfoRepository) {
        this.playRecordRepository = playRecordRepository;
        this.artistInfoRepository = artistInfoRepository;
    }

    @Transactional
    public void savePlayRecord(final long memberId, final PlayRecordRequest dto) {
        if(artistInfoRepository.findArtistInfoByArtistName(dto.artistName()).isEmpty()){
            ArtistInfo artistInfo = ArtistInfo.builder().artistName(dto.artistName()).build();
            artistInfoRepository.save(artistInfo);
        }
        PlayRecord playRecord = PlayRecordMapper.toEntity(memberId, dto);
        playRecordRepository.save(playRecord);
    }
}
