package server.service.ai;

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

    @Scheduled(cron = "0 5 10,18,2 * * *")
    @CacheEvict(value = "recommendation-ai-album", allEntries = true)
    public void clearRecommendation() {
        log.info("10:05, 18:05, 02:05에 AI 추천 목록 최신화 시작");
    }

    @Scheduled(cron = "5 5 10,18,2 * * *")
    public void getRecommendation() {
        aiService.findIds();
        log.info("10:05, 18:05, 02:05에 AI 추천 목록 최신화 완료");
    }
}
