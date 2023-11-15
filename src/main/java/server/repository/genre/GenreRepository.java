package server.repository.genre;

import org.springframework.stereotype.Repository;
import server.domain.genre.Genre;
import server.global.exception.NotFoundException;

import static server.global.constant.ExceptionMessage.*;

@Repository
public class GenreRepository {

    private final GenreJpaRepository genreJpaRepository;

    public GenreRepository(final GenreJpaRepository genreJpaRepository){
        this.genreJpaRepository = genreJpaRepository;
    }

    public void save(final Genre genre){
        genreJpaRepository.save(genre);
    }

    public Genre getByMemberId(final long memberId) {
        return genreJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(GENRE_NOT_FOUND_EXCEPTION.message));
    }
}
