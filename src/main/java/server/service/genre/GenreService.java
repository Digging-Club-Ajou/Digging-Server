package server.service.genre;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.genre.Genre;
import server.domain.member.persist.Member;
import server.mapper.genre.GenreMapper;
import server.mapper.genre.dto.GenreRequest;
import server.repository.genre.GenreRepository;
import server.repository.member.MemberRepository;

@Service
public class GenreService {

    MemberRepository memberRepository;
    GenreRepository genreRepository;

    public GenreService(MemberRepository memberRepository,GenreRepository genreRepository){
        this.genreRepository = genreRepository;
        this.memberRepository = memberRepository;

    }

    @Transactional
    public void saveUserGenre(final long memberId,GenreRequest genreRequest) {

        Genre genre = GenreMapper.toEntity(memberId, genreRequest);
        genreRepository.save(genre);


    }

}
