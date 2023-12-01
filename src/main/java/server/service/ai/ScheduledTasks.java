package server.service.ai;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduledTasks {

    private final AIService aiService;

    public ScheduledTasks(final AIService aiService) {
        this.aiService = aiService;
    }

    @Scheduled(cron = "0 0 5 * * *")
    @CacheEvict(value = "recommendation-ai-album", allEntries = true)
    public void clearRecommendation() {
        log.info("5시 AI 추천 목록 최신화 시작");
    }

    @Scheduled(cron = "5 0 5 * * *")
    public void getRecommendation() {
        aiService.findIds();
        log.info("5시 AI 추천 목록 최신화 완료");
    }
}
