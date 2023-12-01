package server.service.ai;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitializationTasks {

    private final AIService aiService;

    public InitializationTasks(final AIService aiService) {
        this.aiService = aiService;
    }

    @PostConstruct
    public void initialize() {
        aiService.findIds();
        log.info("AI 추천 목록 최신화 완료");
    }
}
