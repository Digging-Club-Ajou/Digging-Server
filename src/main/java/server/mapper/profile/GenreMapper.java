package server.mapper.profile;

import org.joda.time.DateTime;
import server.domain.profile.Genre;
import server.domain.profile.Profile;
import server.mapper.profile.dto.GenreRequest;

public class GenreMapper {
    private GenreMapper() {
    }

    public static Genre toEntity(GenreRequest genreRequest) {
        return Genre.builder()
                .memberId(genreRequest.memberId())
                .kPop(genreRequest.kPop())
                .jPop(genreRequest.jPop())
                .rock(genreRequest.rock())
                .pop(genreRequest.pop())
                .createAt(DateTime.now())
                .lastModifiedAt(DateTime.now())
                .build();
    }
}
