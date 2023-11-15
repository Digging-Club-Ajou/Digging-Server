package server.service.ai;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.genre.Genre;
import server.domain.member.persist.Member;
import server.mapper.music_recommendation.dto.AIResponse;
import server.repository.genre.GenreRepository;
import server.repository.member.MemberRepository;
import server.repository.music_recommendation.MusicRecommendationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIService {

    private final MemberRepository memberRepository;
    private final MusicRecommendationRepository musicRecommendationRepository;
    private final GenreRepository genreRepository;

    public AIService(final MemberRepository memberRepository,
                     final MusicRecommendationRepository musicRecommendationRepository,
                     final GenreRepository genreRepository) {
        this.memberRepository = memberRepository;
        this.musicRecommendationRepository = musicRecommendationRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<AIResponse> getInfo() {
        List<AIResponse> aiResponses = new ArrayList<>();

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            List<String> artistNames = musicRecommendationRepository.findByArtistsByMemberId(member.getId());
            Genre genre = genreRepository.getByMemberId(member.getId());
            List<String> genreText = genre.getGenreText();
            AIResponse aiResponse = new AIResponse(member.getId(), artistNames, genreText, member.getGender());
            aiResponses.add(aiResponse);
        }

        return aiResponses;
    }
}
