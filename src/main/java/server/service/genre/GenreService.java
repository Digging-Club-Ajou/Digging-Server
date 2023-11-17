package server.service.genre;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.genre.Genre;
import server.mapper.genre.GenreMapper;
import server.mapper.genre.dto.GenreRequest;
import server.repository.genre.GenreRepository;

import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(final GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public void saveUserGenre(final long memberId, final GenreRequest genreRequest) {
        Optional<Genre> optionalGenre = genreRepository.findByMemberId(memberId);
        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            genre.updateGenre(genreRequest);
        } else {
            Genre genre = GenreMapper.toEntity(memberId, genreRequest);
            genreRepository.save(genre);
        }
    }
}
