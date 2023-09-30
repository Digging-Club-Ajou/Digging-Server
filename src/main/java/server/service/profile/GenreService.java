package server.service.profile;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.profile.Genre;
import server.mapper.profile.GenreMapper;
import server.mapper.profile.dto.GenreRequest;
import server.repository.profile.GenreRepository;

@Service
public class GenreService {

    GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository){
        this.genreRepository = genreRepository;

    }

    @Transactional
    public void saveUserGenre(GenreRequest genreRequest){

        Genre genre = GenreMapper.toEntity(genreRequest);


        genreRepository.save(genre);


    }

}
