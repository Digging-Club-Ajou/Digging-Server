package server.controller.artist;

import org.springframework.web.bind.annotation.*;


import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.artist.dto.ArtistRequest;
import server.service.artist.ArtistCreateService;



@RequestMapping("/api")
@RestController
public class ArtistController {

    private final ArtistCreateService artistCreateService;

    public ArtistController(final ArtistCreateService artistCreateService) {
        this.artistCreateService = artistCreateService;


    }

    @PostMapping("/artists")
    public void createArtist(@Login final MemberSession memberSession,
                             @RequestBody final ArtistRequest dto) {
        artistCreateService.createArtists(memberSession.id(), dto);
    }



}
