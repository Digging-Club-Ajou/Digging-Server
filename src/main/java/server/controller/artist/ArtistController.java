package server.controller.artist;

import com.amazonaws.services.s3.model.CSVInput;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.artist.ArtistInfo;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.artist.dto.ArtistInfoDto;
import server.mapper.artist.dto.ArtistRequest;
import server.mapper.artist.dto.ArtistResponses;
import server.service.artist.ArtistCreateService;
import server.service.artist.ArtistInfoCreateService;
import server.service.artist.ArtistInfoService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ArtistController {

    private final ArtistCreateService artistCreateService;
    private final ArtistInfoCreateService artistInfoCreateService;
    private final ArtistInfoService artistInfoService;

    public ArtistController(final ArtistCreateService artistCreateService, final ArtistInfoCreateService artistInfoCreateService, final ArtistInfoService artistInfoService) {
        this.artistCreateService = artistCreateService;
        this.artistInfoCreateService = artistInfoCreateService;
        this.artistInfoService = artistInfoService;
    }

    @PostMapping("/artists")
    public void createArtist(@Login final MemberSession memberSession,
                             @RequestBody final ArtistRequest dto) {
        artistCreateService.createArtists(memberSession.id(), dto);
    }


    @PostMapping(value = "/artists-info")
    public void createArtistInfo(@RequestPart MultipartFile csvFile) throws Exception {
        artistInfoCreateService.createArtistInfo(csvFile);
    }

    @GetMapping("/artists-info")
    public ArtistResponses getArtistInfos() {
        return artistInfoService.getArtistInfos();
    }
}
