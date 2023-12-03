package server.controller.artist_info;
import org.springframework.web.bind.annotation.*;
import server.mapper.artist_info.dto.ArtistInfoResponses;
import server.mapper.artist_info.dto.ArtistInfoRequests;
import server.service.artist_info.ArtistInfoScheduledTasks;
import server.service.artist_info.ArtistInfoService;

@RequestMapping("/api")
@RestController
public class ArtistInfoController {
    private final ArtistInfoService artistInfoService;
    private final ArtistInfoScheduledTasks artistInfoScheduledTasks;

    public ArtistInfoController(final ArtistInfoService artistInfoService, final ArtistInfoScheduledTasks artistInfoScheduledTasks) {
        this.artistInfoService = artistInfoService;
        this.artistInfoScheduledTasks = artistInfoScheduledTasks;
    }

    @GetMapping("/artists-info")
    public void testArtistInfo(){
        artistInfoScheduledTasks.createArtistInfoGenre();
    }

    @PostMapping("/artists-info")
    public void createArtistsInfo(@RequestBody ArtistInfoResponses artistInfoResponses){
        artistInfoService.createArtistInfo(artistInfoResponses);
    }
}

