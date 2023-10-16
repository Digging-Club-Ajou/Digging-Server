package server.mapper.genre;

import server.domain.genre.Genre;
import server.mapper.genre.dto.GenreRequest;

public class GenreMapper {
    private GenreMapper() {
    }

    public static Genre toEntity(final long memberId, final GenreRequest dto) {
        return Genre.builder()
                .memberId(memberId)
                .ballade(dto.ballade())
                .dance(dto.dance())
                .rockMetal(dto.rockMetal())
                .rbAndSoul(dto.rbAndSoul())
                .rapHiphop(dto.rapHiphop())
                .folkBlues(dto.folkBlues())
                .indie(dto.indie())
                .pop(dto.pop())
                .ostAndMusical(dto.ostAndMusical())
                .build();
    }
}
