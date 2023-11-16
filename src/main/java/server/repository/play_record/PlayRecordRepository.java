package server.repository.play_record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.domain.play_record.PlayRecord;
import server.domain.play_record.QPlayRecord;

import java.util.Optional;

import static server.domain.music_recommentdation.QMusicRecommendation.musicRecommendation;
import static server.domain.play_record.QPlayRecord.playRecord;

@Repository
public class PlayRecordRepository {

    private final PlayRecordJpaRepository playRecordJpaRepository;
    private final JPAQueryFactory queryFactory;

    public PlayRecordRepository(final PlayRecordJpaRepository playRecordJpaRepository,
                                final JPAQueryFactory queryFactory) {
        this.playRecordJpaRepository = playRecordJpaRepository;
        this.queryFactory = queryFactory;
    }

    public void save(final PlayRecord playRecord) {
        playRecordJpaRepository.save(playRecord);
    }

    public Optional<String> findFavoriteArtistName(final long memberId) {
        String artistName = queryFactory.select(playRecord.artistName)
                .from(playRecord)
                .where(playRecord.memberId.eq(memberId))
                .groupBy(playRecord.artistName)
                .orderBy(playRecord.artistName.count().desc())
                .fetchFirst();

        return Optional.ofNullable(artistName);
    }

    public Optional<String> findFavoriteSongTile(final long memberId) {
        String songTitle = queryFactory.select(playRecord.songTitle)
                .from(playRecord)
                .where(playRecord.memberId.eq(memberId))
                .groupBy(playRecord.songTitle)
                .orderBy(playRecord.songTitle.count().desc())
                .fetchFirst();

        return Optional.ofNullable(songTitle);
    }
}
