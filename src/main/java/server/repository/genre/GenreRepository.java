package server.repository.genre;

import org.springframework.stereotype.Repository;
import server.domain.genre.Genre;

import java.util.Optional;

@Repository
public class GenreRepository {

    private final GenreJpaRepository genreJpaRepository;

    public GenreRepository(final GenreJpaRepository genreJpaRepository){
        this.genreJpaRepository = genreJpaRepository;
    }

    public void save(final Genre genre){
        genreJpaRepository.save(genre);
    }

    public Optional<Genre> findByMemberId(final long memberId) {
        return genreJpaRepository.findByMemberId(memberId);
    }
}
