package server.service.genre;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.genre.Genre;
import server.mapper.genre.GenreMapper;
import server.mapper.genre.dto.GenreRequest;
import server.repository.genre.GenreRepository;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(final GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public void saveUserGenre(final long memberId, final GenreRequest genreRequest) {
        Genre genre = GenreMapper.toEntity(memberId, genreRequest);
        genreRepository.save(genre);
    }
}
