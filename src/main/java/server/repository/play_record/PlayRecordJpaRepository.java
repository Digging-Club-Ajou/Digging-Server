package server.repository.play_record;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.play_record.PlayRecord;

public interface PlayRecordJpaRepository extends JpaRepository<PlayRecord, Long> {
}
