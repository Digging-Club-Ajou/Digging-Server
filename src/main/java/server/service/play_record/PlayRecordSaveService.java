package server.service.play_record;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.play_record.PlayRecord;
import server.mapper.play_record.PlayRecordMapper;
import server.mapper.play_record.dto.PlayRecordRequest;
import server.repository.play_record.PlayRecordRepository;

@Service
public class PlayRecordSaveService {

    private final PlayRecordRepository playRecordRepository;

    public PlayRecordSaveService(final PlayRecordRepository playRecordRepository) {
        this.playRecordRepository = playRecordRepository;
    }

    @Transactional
    public void savePlayRecord(final long memberId, final PlayRecordRequest dto) {
        PlayRecord playRecord = PlayRecordMapper.toEntity(memberId, dto);
        playRecordRepository.save(playRecord);
    }
}
