package server.controller.spotify;

import org.springframework.web.bind.annotation.*;
import server.mapper.spotify.SpotifyMusicResult;
import server.mapper.spotify.SpotifySearchDto;
import server.mapper.spotify.SpotifyTrackPreviewURL;
import server.service.spotify.SpotifyPlayMusicService;
import server.service.spotify.SpotifySearchMusicService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class SpotifyController {

    private final SpotifySearchMusicService spotifySearchMusicService;
    private final SpotifyPlayMusicService spotifyPlayMusicService;

    public SpotifyController(final SpotifySearchMusicService spotifySearchMusicService, final SpotifyPlayMusicService spotifyPlayMusicService) {
        this.spotifySearchMusicService = spotifySearchMusicService;
        this.spotifyPlayMusicService = spotifyPlayMusicService;
    }

    @GetMapping("/musics")
    public SpotifyMusicResult getMusics(@RequestParam final String search) {
        List<SpotifySearchDto> spotifySearchDtos = spotifySearchMusicService.searchTracks(search);
        return new SpotifyMusicResult(spotifySearchDtos);
    }


}
