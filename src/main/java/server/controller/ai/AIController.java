package server.controller.ai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.mapper.music_recommendation.dto.AIResponse;
import server.mapper.music_recommendation.dto.AIResult;
import server.service.ai.AIService;

import java.util.List;

@RestController
public class AIController {

    private final AIService aiService;

    public AIController(final AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/api/ai")
    public AIResult getMembersInfo() {
        List<AIResponse> aiResponses = aiService.getInfo();
        return new AIResult(aiResponses);
    }
}
