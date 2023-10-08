package server.mapper.genre;

import org.joda.time.DateTime;
import server.domain.genre.Genre;
import server.mapper.genre.dto.GenreRequest;

public class GenreMapper {
    private GenreMapper() {
    }

    public static Genre toEntity(Long memberId,GenreRequest genreRequest) {
        return Genre.builder()
                .memberId(memberId)
                .kPop(genreRequest.kPop())
                .jPop(genreRequest.jPop())
                .rock(genreRequest.rock())
                .pop(genreRequest.pop())
                .build();
    }
}
