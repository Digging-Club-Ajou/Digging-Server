package server.service.spotify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import server.global.constant.SpotifyConstant;

@Slf4j
@Service
public class SpotifyScheduledService {

    private static final int FIFTY_MINUTE = 60 * 1000 * 50;
    private final SpotifyTokenService spotifyTokenService;

    public SpotifyScheduledService(final SpotifyTokenService spotifyTokenService) {
        this.spotifyTokenService = spotifyTokenService;
    }

    @Scheduled(fixedRate = FIFTY_MINUTE)
    public void updateToken() {
        String token = spotifyTokenService.getToken();
        SpotifyConstant.updateToken(token);
        log.info("spotify token={}", token);
    }
}
