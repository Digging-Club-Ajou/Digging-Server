package server.controller.spotify;

import org.springframework.web.bind.annotation.*;
import server.mapper.spotify.SpotifyMusicResult;
import server.mapper.spotify.SpotifySearchDto;
import server.service.spotify.SpotifySearchMusicService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class SpotifyController {

    private final SpotifySearchMusicService spotifySearchMusicService;

    public SpotifyController(final SpotifySearchMusicService spotifySearchMusicService) {
        this.spotifySearchMusicService = spotifySearchMusicService;
    }

    @GetMapping("/musics")
    public SpotifyMusicResult getMusics(@RequestParam final String search) {
        List<SpotifySearchDto> spotifySearchDtos = spotifySearchMusicService.searchTracks(search);
        return new SpotifyMusicResult(spotifySearchDtos);
    }

    @PutMapping("/musics")
    public void putMusics(@RequestParam final String url){

    }
}
