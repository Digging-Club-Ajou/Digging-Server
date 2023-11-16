package server.repository.music_recommendation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.domain.music_recommentdation.MusicRecommendation;
import server.domain.music_recommentdation.QMusicRecommendation;

import java.util.List;
import java.util.Optional;

import static server.domain.music_recommentdation.QMusicRecommendation.musicRecommendation;

@Repository
public class MusicRecommendationRepository {

    private final MusicRecommendationJpaRepository musicRecommendationJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MusicRecommendationRepository(final MusicRecommendationJpaRepository musicRecommendationJpaRepository,
                                         final JPAQueryFactory queryFactory) {
        this.musicRecommendationJpaRepository = musicRecommendationJpaRepository;
        this.queryFactory = queryFactory;
    }

    public void save(final MusicRecommendation musicRecommendation) {
        musicRecommendationJpaRepository.save(musicRecommendation);
    }

    public List<String> findByArtistsByMemberId(final long memberId) {
        return queryFactory.select(musicRecommendation.artistName)
                .from(musicRecommendation)
                .where(musicRecommendation.memberId.eq(memberId))
                .fetch();
    }

    public Optional<String> findFavoriteArtistName(final long memberId) {
        String artistName = queryFactory.select(musicRecommendation.artistName)
                .from(musicRecommendation)
                .where(musicRecommendation.memberId.eq(memberId))
                .groupBy(musicRecommendation.artistName)
                .orderBy(musicRecommendation.artistName.count().desc())
                .fetchFirst();

        return Optional.ofNullable(artistName);
    }
}
