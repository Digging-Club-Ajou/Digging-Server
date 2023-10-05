package server.controller.genre;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.mapper.genre.dto.GenreRequest;
import server.service.genre.GenreService;

@RequestMapping("/api")
@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(final GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/genres")
    public void createGenre(@RequestBody GenreRequest genreRequest) throws JsonProcessingException {
        System.out.println(genreRequest.toString());
        genreService.saveUserGenre(genreRequest);

    }
}
