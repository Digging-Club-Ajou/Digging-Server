package server.mapper.play_record;

import server.domain.play_record.PlayRecord;
import server.mapper.play_record.dto.PlayRecordRequest;

public class PlayRecordMapper {

    private PlayRecordMapper() {
    }

    public static PlayRecord toEntity(final long memberId, final PlayRecordRequest dto) {
        return PlayRecord.builder()
                .memberId(memberId)
                .artistName(dto.artistName())
                .songTitle(dto.songTitle())
                .build();
    }
}
