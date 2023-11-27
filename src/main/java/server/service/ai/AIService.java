package server.service.ai;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import server.domain.genre.Genre;
import server.domain.member.persist.Member;
import server.mapper.music_recommendation.dto.AIRecommendationResult;
import server.mapper.music_recommendation.dto.AIResponse;
import server.repository.genre.GenreRepository;
import server.repository.member.MemberRepository;
import server.repository.music_recommendation.MusicRecommendationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AIService {

    private final MemberRepository memberRepository;
    private final MusicRecommendationRepository musicRecommendationRepository;
    private final GenreRepository genreRepository;
    private final RestTemplate restTemplate;

    public AIService(final MemberRepository memberRepository,
                     final MusicRecommendationRepository musicRecommendationRepository,
                     final GenreRepository genreRepository,
                     final RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.musicRecommendationRepository = musicRecommendationRepository;
        this.genreRepository = genreRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional(readOnly = true)
    public List<AIResponse> getInfo() {
        List<AIResponse> aiResponses = new ArrayList<>();

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            List<String> artistNames = musicRecommendationRepository.findByArtistsByMemberId(member.getId());
            Optional<Genre> optionalGenre = genreRepository.findByMemberId(member.getId());
            List<String> genreText = new ArrayList<>();
            if (optionalGenre.isPresent()) {
                Genre genre = optionalGenre.get();
                genreText = genre.getGenreText();
            }
            AIResponse aiResponse = new AIResponse(member.getId(),
                    artistNames,
                    genreText,
                    member.getGender(),
                    member.getBirthDate()
            );
            aiResponses.add(aiResponse);
        }

        return aiResponses;
    }

    // todo ML 로직 확정되면 변경 (임시 코드)
    @Cacheable(value = "recommendation-album", key = "#memberId")
    public AIRecommendationResult findIds(final long memberId) {
        String url = "http://helloworld.com/recommendations/{memberId}";
        ResponseEntity<AIRecommendationResult> aiRecommendationResultResponseEntity =
                restTemplate.getForEntity(url, AIRecommendationResult.class, memberId);

        return aiRecommendationResultResponseEntity.getBody();
    }
}
