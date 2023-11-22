package server.service.spotify.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.mapper.spotify.SpotifySearchDto;
import server.service.spotify.SpotifySearchMusicService;

import java.util.ArrayList;
import java.util.List;

// Test Double
@Profile("test")
@Service
public class SpotifySearchMusicTestService implements SpotifySearchMusicService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SpotifySearchMusicTestService(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // API 문서화 용도
    public List<SpotifySearchDto> searchTracks(final String search, final int page) {
        SpotifySearchDto spotifySearchDto1 = new SpotifySearchDto("NewJeans", "Super Shy",
                "https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af",
                "https://p.scdn.co/mp3-preview/dab062e2cc708a2680ce84953a3581c5a679a230?cid=eb9c2bc88c014e5e9a78cdb743d7a6df");
        SpotifySearchDto spotifySearchDto2 = new SpotifySearchDto("NewJeans", "New Jeans",
                "https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af",
                "https://p.scdn.co/mp3-preview/a37b4269b0dc5e952826d8c43d1b7c072ac39b4c?cid=eb9c2bc88c014e5e9a78cdb743d7a6df");
        SpotifySearchDto spotifySearchDto3 = new SpotifySearchDto("NewJeans", "Attention",
                "https://i.scdn.co/image/ab67616d0000b2739d28fd01859073a3ae6ea209",
                "https://p.scdn.co/mp3-preview/7794e6d9c9f2b8ce10cc7e2564b75e2ca2877f2d?cid=eb9c2bc88c014e5e9a78cdb743d7a6df");
        SpotifySearchDto spotifySearchDto4 = new SpotifySearchDto("NewJeans", "ETA",
                "https://i.scdn.co/image/ab67616d0000b2730744690248ef3ba7b776ea7b",
                "https://p.scdn.co/mp3-preview/e076cd31805e449dae7d991711df5f2ff920b375?cid=eb9c2bc88c014e5e9a78cdb743d7a6df");
        SpotifySearchDto spotifySearchDto5 = new SpotifySearchDto("NewJeans", "OMG",
                "https://i.scdn.co/image/ab67616d0000b273d70036292d54f29e8b68ec01",
                "https://p.scdn.co/mp3-preview/b9e344aa96afc45a56df77ca63c78786bdaa0047?cid=eb9c2bc88c014e5e9a78cdb743d7a6df");

        List<SpotifySearchDto> spotifySearchDtos = new ArrayList<>();

        spotifySearchDtos.add(spotifySearchDto1);
        spotifySearchDtos.add(spotifySearchDto2);
        spotifySearchDtos.add(spotifySearchDto3);
        spotifySearchDtos.add(spotifySearchDto4);
        spotifySearchDtos.add(spotifySearchDto5);

        return spotifySearchDtos;
    }

    @Override
    public String findGenre(final String favoriteArtistName) {
        return "k-pop";
    }
}
