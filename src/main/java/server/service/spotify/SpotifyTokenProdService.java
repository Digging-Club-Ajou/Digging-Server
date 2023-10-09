package server.service.spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import server.mapper.spotify.SpotifyTokenInfo;

@Service
public class SpotifyTokenProdService {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.get-token-url}")
    private String getTokenUrl;

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    private final RestTemplate restTemplate;

    public SpotifyTokenProdService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getToken() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(GRANT_TYPE, CLIENT_CREDENTIALS);
        requestBody.add(CLIENT_ID, clientId);
        requestBody.add(CLIENT_SECRET, clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<SpotifyTokenInfo> response =
                restTemplate.postForEntity(getTokenUrl, requestEntity, SpotifyTokenInfo.class);

        SpotifyTokenInfo spotifyTokenInfo = response.getBody();
        assert spotifyTokenInfo != null;
        return spotifyTokenInfo.access_token();
    }
}
