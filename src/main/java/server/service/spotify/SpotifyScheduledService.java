package server.service.spotify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import server.global.constant.SpotifyConstant;

@Slf4j
@Profile({"prod", "dev"})
@Service
public class SpotifyScheduledService {

    private static final int FIFTY_MINUTE = 60 * 1000 * 50;
    private final SpotifyTokenProdService spotifyTokenProdService;

    public SpotifyScheduledService(final SpotifyTokenProdService spotifyTokenProdService) {
        this.spotifyTokenProdService = spotifyTokenProdService;
    }

    @Scheduled(fixedRate = FIFTY_MINUTE)
    public void updateToken() {
        String token = spotifyTokenProdService.getToken();
        SpotifyConstant.updateToken(token);
        log.info("spotify token={}", token);
    }
}
