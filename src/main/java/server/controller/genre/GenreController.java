package server.controller.genre;

import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.genre.dto.GenreRequest;
import server.mapper.genre.dto.GenreResult;
import server.service.genre.GenreFindService;
import server.service.genre.GenreService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class GenreController {

    private final GenreService genreService;
    private final GenreFindService genreFindService;

    public GenreController(final GenreService genreService, final GenreFindService genreFindService) {
        this.genreService = genreService;
        this.genreFindService = genreFindService;
    }

    @GetMapping("/favorite-genres")
    public GenreResult findFavoriteGenre(@Login final MemberSession memberSession) {
        String favoriteGenre = genreFindService.findFavoriteGenre(memberSession.id());
        return new GenreResult(favoriteGenre);
    }

    @PostMapping("/genres")
    public void createGenre(@Login final MemberSession memberSession,
                            @RequestBody final GenreRequest genreRequest) {
        genreService.saveUserGenre(memberSession.id(),genreRequest);
    }
}
