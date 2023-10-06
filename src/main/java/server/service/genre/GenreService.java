package server.service.genre;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.genre.Genre;
import server.mapper.genre.GenreMapper;
import server.mapper.genre.dto.GenreRequest;
import server.repository.genre.GenreRepository;

@Service
public class GenreService {

    GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository){
        this.genreRepository = genreRepository;

    }

    @Transactional
    public void saveUserGenre(GenreRequest genreRequest) throws JsonProcessingException {

        Genre genre = GenreMapper.toEntity(genreRequest);


        genreRepository.save(genre);


    }

}
