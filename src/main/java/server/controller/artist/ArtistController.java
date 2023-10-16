package server.controller.artist;

import com.amazonaws.services.s3.model.CSVInput;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.artist.dto.ArtistRequest;
import server.service.artist.ArtistCreateService;
import server.service.artist.ArtistInfoCreateService;

@RequestMapping("/api")
@RestController
public class ArtistController {

    private final ArtistCreateService artistCreateService;
    private final ArtistInfoCreateService artistInfoCreateService;

    public ArtistController(final ArtistCreateService artistCreateService, final ArtistInfoCreateService artistInfoCreateService) {
        this.artistCreateService = artistCreateService;
        this.artistInfoCreateService = artistInfoCreateService;
    }

    @PostMapping("/artists")
    public void createArtist(@Login final MemberSession memberSession,
                             @RequestBody final ArtistRequest dto) {
        artistCreateService.createArtists(memberSession.id(), dto);
    }


    @PostMapping(value = "/artists-info")
    public void createArtistInfo(@RequestPart MultipartFile csvFile) throws Exception {
        System.out.println("At controller");
        artistInfoCreateService.createArtistInfo(csvFile);
    }
}
