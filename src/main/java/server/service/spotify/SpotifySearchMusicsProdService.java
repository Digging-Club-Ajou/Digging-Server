package server.service.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.mapper.spotify.SpotifySearchDto;

import java.util.ArrayList;
import java.util.List;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.SpotifyConstant.*;

@Profile({"prod", "dev"})
@Slf4j
@Service
public class SpotifySearchMusicsProdService implements SpotifySearchMusicService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SpotifySearchMusicsProdService(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<SpotifySearchDto> searchTracks(final String search) {
        ResponseEntity<String> response = getStringResponseEntity(search);

        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response.getBody());
            JsonNode itemsNode = rootNode.path(TRACKS).path(ITEMS);

            return getSearchDtos(itemsNode);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(MUSIC_JSON_PARSING.message);
        }
    }

    private ResponseEntity<String> getStringResponseEntity(final String search) {
        String apiUrl = SPOTIFY_TRACKS_URL + search + BASIC_CONDITION;
        String spotifyAccessToken = getSpotifyToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER + spotifyAccessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }

    private List<SpotifySearchDto> getSearchDtos(final JsonNode itemsNode) {
        List<SpotifySearchDto> spotifySearchDtos = new ArrayList<>();

        for (JsonNode item : itemsNode) {
            String name = item.path(NAME).asText();
            String artistName = item.path(ARTISTS).get(ZERO).path(NAME).asText();
            String imageUrl = item.path(ALBUM).path(IMAGES).get(ZERO).path(URL).asText();
            String previewUrl = item.path(PREVIEW_URL).asText();

            SpotifySearchDto dto = new SpotifySearchDto(artistName, name, imageUrl, previewUrl);
            spotifySearchDtos.add(dto);
        }
        return spotifySearchDtos;
    }
}
