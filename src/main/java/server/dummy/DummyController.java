package server.dummy;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.dummy.dto.DummyDto;
import server.service.ai.AIService;

@RestController
public class DummyController {

    private final DummyService dummyService;
    private final AIService aiService;

    public DummyController(final DummyService dummyService, final AIService aiService) {
        this.dummyService = dummyService;
        this.aiService = aiService;
    }

    @PostMapping("/digging/dummy")
    public void save(@RequestBody final DummyDto dummyDto) {
        dummyService.dummySave(dummyDto);
    }

    @PostMapping("/scheduling/tasks")
    public void scheduling() {
        aiService.findIds();
    }
}
