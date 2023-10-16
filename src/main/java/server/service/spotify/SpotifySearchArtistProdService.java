package server.service.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import server.mapper.spotify.SpotifyArtistDto;
import server.mapper.spotify.SpotifySearchDto;

import java.util.List;

import static server.global.constant.ExceptionMessage.MUSIC_JSON_PARSING;
import static server.global.constant.SpotifyConstant.ITEMS;
import static server.global.constant.SpotifyConstant.TRACKS;

public class SpotifySearchArtistProdService implements SpotifySearchArtistService{

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SpotifySearchArtistProdService(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

//    public List<SpotifySearchDto> searchTracks(final String search) {
//        ResponseEntity<String> response = getStringResponseEntity(search);
//
//        JsonNode rootNode;
//        try {
//            rootNode = objectMapper.readTree(response.getBody());
//            JsonNode itemsNode = rootNode.path(TRACKS).path(ITEMS);
//
//            return getSearchDtos(itemsNode);
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(MUSIC_JSON_PARSING.message);
//        }
//    }
    @Override
    public List<SpotifySearchDto> searchArtist(String search) {
        return null;
    }
}
