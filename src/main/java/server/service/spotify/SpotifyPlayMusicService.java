package server.service.spotify;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import server.mapper.member.dto.KakaoToken;
import server.mapper.spotify.SpotifyTrackPreviewURL;

import static server.global.constant.SpotifyConstant.*;

@Service
public class SpotifyPlayMusicService {
    public SpotifyPlayMusicService( ){

    }

    @SneakyThrows
    public String playMusic(String id){

        HttpHeaders headers = new HttpHeaders();
//        headers.add("id", id);
        headers.set(AUTHORIZATION, BEARER + spotifyAccessToken);
        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> spotifyRequest = new HttpEntity<>(headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                SPOTIFY_PLAY_TRACK_URL+id,
                HttpMethod.GET,
                spotifyRequest,
                String.class
        );


        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        SpotifyTrackPreviewURL spotifyTrackPreviewURL = objectMapper.readValue(responseBody, SpotifyTrackPreviewURL.class);

        return spotifyTrackPreviewURL;
//        System.out.println(spotifyTrackPreviewURL);

    }
}
