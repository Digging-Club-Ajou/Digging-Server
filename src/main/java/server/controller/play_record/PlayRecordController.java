package server.controller.play_record;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.play_record.dto.PlayRecordRequest;
import server.service.play_record.PlayRecordSaveService;

@RequestMapping("/api")
@RestController
public class PlayRecordController {

    private final PlayRecordSaveService playRecordSaveService;

    public PlayRecordController(final PlayRecordSaveService playRecordSaveService) {
        this.playRecordSaveService = playRecordSaveService;
    }

    @PostMapping("/play-record")
    public void savePlayRecord(@Login final MemberSession memberSession,
                               @RequestBody final PlayRecordRequest dto) {
        playRecordSaveService.savePlayRecord(memberSession.id(), dto);
    }
}
