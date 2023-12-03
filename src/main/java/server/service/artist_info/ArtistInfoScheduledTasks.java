package server.service.artist_info;

import com.google.api.client.util.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import server.mapper.artist_info.dto.ArtistInfoRequests;
import server.mapper.artist_info.dto.ArtistInfoResponses;

import static server.global.constant.KakaoConstant.*;

@Service
public class ArtistInfoScheduledTasks {

    @Value("")
    private String getArtistInfoUrl;

    private final ArtistInfoService artistInfoService;
    private final RestTemplate restTemplate;

    public ArtistInfoScheduledTasks(final ArtistInfoService artistInfoService, final RestTemplate restTemplate) {
        this.artistInfoService = artistInfoService;
        this.restTemplate = restTemplate;
    }
    @Scheduled(cron = "0 0 5 * * 0")
    public void createArtistInfoGenre(){
        ArtistInfoRequests artistInfoRequests = artistInfoService.getNullArtists();
        if(!artistInfoRequests.artistInfoRespons().isEmpty()){
            ArtistInfoResponses artistInfoResponses = getArtistInfoResponses(artistInfoRequests);
            artistInfoService.createArtistInfo(artistInfoResponses);
        }

    }

    public ArtistInfoResponses getArtistInfoResponses(ArtistInfoRequests artistInfoRequests){
        HttpHeaders headers = new HttpHeaders();

        String header;
        headers.add(CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("artistInfoRequests", artistInfoRequests);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<ArtistInfoResponses> response = restTemplate.postForEntity(
                getArtistInfoUrl,
                requestEntity,
                ArtistInfoResponses.class
        );

        ArtistInfoResponses artistInfoResponses = response.getBody();
        assert  artistInfoResponses != null;
        return artistInfoResponses;
    }
}
