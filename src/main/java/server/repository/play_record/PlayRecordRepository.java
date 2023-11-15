package server.repository.play_record;

import org.springframework.stereotype.Repository;
import server.domain.play_record.PlayRecord;

@Repository
public class PlayRecordRepository {

    private final PlayRecordJpaRepository playRecordJpaRepository;

    public PlayRecordRepository(final PlayRecordJpaRepository playRecordJpaRepository) {
        this.playRecordJpaRepository = playRecordJpaRepository;
    }

    public void save(final PlayRecord playRecord) {
        playRecordJpaRepository.save(playRecord);
    }
}
