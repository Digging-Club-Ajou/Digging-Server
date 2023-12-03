package server.service.artist_info;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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


    private final String getArtistInfoUrl = "http://54.180.109.224:7000/api/genres";

    private final ArtistInfoService artistInfoService;
    private final RestTemplate restTemplate;

    public ArtistInfoScheduledTasks(final ArtistInfoService artistInfoService, final RestTemplate restTemplate) {
        this.artistInfoService = artistInfoService;
        this.restTemplate = restTemplate;
    }
    @Scheduled(cron = "0 0 5 * * 0")
    public void createArtistInfoGenre() {
        ArtistInfoRequests artistInfoRequests = artistInfoService.getNullArtists();
        if(!artistInfoRequests.artistInfoRequests().isEmpty()){
            ArtistInfoResponses artistInfoResponses = getArtistInfoResponses(artistInfoRequests);
            artistInfoService.createArtistInfo(artistInfoResponses);
        }

    }

    public ArtistInfoResponses getArtistInfoResponses(ArtistInfoRequests artistInfoRequests) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            String request = objectMapper.writeValueAsString(artistInfoRequests);

            // HTTP 요청 보내기
            HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

            ResponseEntity<ArtistInfoResponses> response =
                    restTemplate.postForEntity(getArtistInfoUrl, requestEntity, ArtistInfoResponses.class);

            ArtistInfoResponses artistInfoResponses = response.getBody();
            assert  artistInfoResponses != null;
            return artistInfoResponses;

        }catch (JsonProcessingException e){
            throw new RuntimeException("Error parsing Crawling API response for artist details.");

        }




    }
}
