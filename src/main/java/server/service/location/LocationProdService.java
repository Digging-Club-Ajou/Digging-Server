package server.service.location;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import server.global.exception.BadRequestException;
import server.mapper.location.LocationResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.SpotifyConstant.*;

@Profile({"prod", "dev"})
@Service
public class LocationProdService implements LocationService {

    @Value("${kakao.rest-api}")
    private String KAKAO_REST_API;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public LocationProdService(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<LocationResponse> findLocation(final String query, final String x, final String y) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, KAKAO_AK + KAKAO_REST_API);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponents uriComponents = getUriComponents(query, x, y);

        ResponseEntity<String> locationInfo =
                restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);

        return getLocationResponses(locationInfo);
    }

    private static UriComponents getUriComponents(final String query, final String x, final String y) {
        return UriComponentsBuilder.fromHttpUrl(KAKAO_LOCATION_KEYWORD_URL)
                .queryParam(QUERY, BRACE_QUERY)
                .queryParam(X, x)
                .queryParam(Y, y)
                .queryParam(SIZE, MUSIC_SEARCH_SIZE)
                .queryParam(SORT, DISTANCE)
                .build()
                .expand(Collections.singletonMap(QUERY, query));
    }

    private List<LocationResponse> getLocationResponses(final ResponseEntity<String> locationInfo) {
        List<LocationResponse> locationResponses = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(locationInfo.getBody());
            JsonNode documents = root.get(DOCUMENTS);

            for (JsonNode document : documents) {
                String placeName = document.get(PLACE_NAME).asText();
                String distance = document.get(DISTANCE).asText();
                locationResponses.add(new LocationResponse(placeName, distance + M));
            }
        } catch (IOException e) {
            throw new BadRequestException(LOCATION_JSON_PARSING.message);
        }

        return locationResponses;
    }
}
