package server.controller.artist_info;
import org.springframework.web.bind.annotation.*;
import server.mapper.artist_info.dto.ArtistInfoResponses;
import server.mapper.artist_info.dto.ArtistInfoRequests;

import server.service.artist_info.ArtistInfoService;

@RequestMapping("/api")
@RestController
public class ArtistInfoController {
    private final ArtistInfoService artistInfoService;


    public ArtistInfoController(final ArtistInfoService artistInfoService) {
        this.artistInfoService = artistInfoService;

    }

    @GetMapping("/artists-info")
    public ArtistInfoRequests getNullArtistInfo(){
        return artistInfoService.getNullArtists();
    }

    @PostMapping("/artists-info")
    public void createArtistsInfo(@RequestBody ArtistInfoResponses artistInfoResponses){
        artistInfoService.createArtistInfo(artistInfoResponses);
    }
}

