package server.service.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.mapper.spotify.SpotifyArtistDto;
import server.mapper.spotify.SpotifySearchDto;

import java.util.ArrayList;
import java.util.List;

import static server.global.constant.ExceptionMessage.MUSIC_JSON_PARSING;
import static server.global.constant.SpotifyConstant.*;

@Service
public class SpotifySearchArtistProdService implements SpotifySearchArtistService{

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SpotifySearchArtistProdService(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public String searchArtist(String search) {

        ResponseEntity<String> response = getStringResponseEntity(search);

        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response.getBody());
            JsonNode itemsNode = rootNode.path(ARTISTS).path(ITEMS);

            return getSearchDto(itemsNode);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(MUSIC_JSON_PARSING.message);
        }

    }

    private ResponseEntity<String> getStringResponseEntity(final String search) {
        String apiUrl = SPOTIFY_TRACKS_URL + search + ARTIST_CONDITION;
        String spotifyAccessToken = getSpotifyToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER + spotifyAccessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }


    private String getSearchDto(final JsonNode itemsNode) {

        String imageUrl = itemsNode.get(ZERO).path(IMAGES).get(ZERO).path(URL).asText();

        return imageUrl;
    }
}
