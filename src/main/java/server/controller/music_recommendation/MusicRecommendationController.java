package server.controller.music_recommendation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.music_recommendation.dto.ArtistRequest;
import server.service.music_recommendation.MusicRecommendationCreateService;

@RequestMapping("/api")
@RestController
public class MusicRecommendationController {

    private final MusicRecommendationCreateService musicRecommendationCreateService;

    public MusicRecommendationController(final MusicRecommendationCreateService musicRecommendationCreateService) {
        this.musicRecommendationCreateService = musicRecommendationCreateService;
    }

    @PostMapping("/artists")
    public ResponseEntity<Void> createArtistRecommendation(@Login final MemberSession memberSession,
                                           @RequestBody final ArtistRequest dto) {
        musicRecommendationCreateService.createRecommendation(memberSession.id(), dto);
        return ResponseEntity.noContent().build();
    }
}
