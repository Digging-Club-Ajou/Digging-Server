package server.repository.artist_info;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.domain.artist_info.ArtistInfo;

import java.util.List;
import java.util.Optional;

import static server.domain.artist_info.QArtistInfo.artistInfo;

@Repository
public class ArtistInfoRepository {

    private final ArtistInfoJpaRepository artistInfoJpaRepository;
    private final JPAQueryFactory queryFactory;


    public ArtistInfoRepository(final ArtistInfoJpaRepository artistInfoJpaRepository, final JPAQueryFactory queryFactory) {
        this.artistInfoJpaRepository = artistInfoJpaRepository;
        this.queryFactory = queryFactory;
    }

    public void save(final ArtistInfo artistInfo) {
        artistInfoJpaRepository.save(artistInfo);
    }

    public Optional<ArtistInfo> findArtistInfoByArtistName(final String artistName) {
        return artistInfoJpaRepository.findArtistInfoByArtistName(artistName);
    }

    public List<String> findGenresAllNullArtist() {

        return queryFactory.select(artistInfo.artistName)
                .from(artistInfo)
                .where(artistInfo.ballade.isNull(),
                        artistInfo.dance.isNull(),
                        artistInfo.dance.isNull(),
                        artistInfo.rockMetal.isNull(),
                        artistInfo.rapHiphop.isNull(),
                        artistInfo.folkBlues.isNull(),
                        artistInfo.indie.isNull(),
                        artistInfo.pop.isNull(),
                        artistInfo.ostAndMusical.isNull())
                .fetch();
    }




}
