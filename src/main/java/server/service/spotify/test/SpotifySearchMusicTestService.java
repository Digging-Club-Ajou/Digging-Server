package server.service.spotify.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
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
    public List<SpotifySearchDto> searchTracks(final String search) {
        SpotifySearchDto spotifySearchDto1 = new SpotifySearchDto("NewJeans", "Super Shy",
                "https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af");
        SpotifySearchDto spotifySearchDto2 = new SpotifySearchDto("NewJeans", "New Jeans",
                "https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af");
        SpotifySearchDto spotifySearchDto3 = new SpotifySearchDto("NewJeans", "Attention",
                "https://i.scdn.co/image/ab67616d0000b2739d28fd01859073a3ae6ea209");
        SpotifySearchDto spotifySearchDto4 = new SpotifySearchDto("NewJeans", "ETA",
                "https://i.scdn.co/image/ab67616d0000b2730744690248ef3ba7b776ea7b");
        SpotifySearchDto spotifySearchDto5 = new SpotifySearchDto("NewJeans", "OMG",
                "https://i.scdn.co/image/ab67616d0000b273d70036292d54f29e8b68ec01");

        List<SpotifySearchDto> spotifySearchDtos = new ArrayList<>();

        spotifySearchDtos.add(spotifySearchDto1);
        spotifySearchDtos.add(spotifySearchDto2);
        spotifySearchDtos.add(spotifySearchDto3);
        spotifySearchDtos.add(spotifySearchDto4);
        spotifySearchDtos.add(spotifySearchDto5);

        return spotifySearchDtos;
    }
}
