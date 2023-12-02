package server.dummy;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.dummy.dto.DummyDto;

@RestController
public class DummyController {

    private final DummyService dummyService;

    public DummyController(final DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @PostMapping("/digging/dummy")
    public void save(@RequestBody final DummyDto dummyDto) {
        dummyService.dummySave(dummyDto);
    }
}
