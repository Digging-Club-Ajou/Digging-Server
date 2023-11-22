package server.controller.play_record;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.play_record.dto.PlayRecordRequest;
import server.mapper.play_record.dto.PlayRecordResponse;
import server.service.play_record.PlayRecordFindService;
import server.service.play_record.PlayRecordSaveService;

@RequestMapping("/api")
@RestController
public class PlayRecordController {

    private final PlayRecordSaveService playRecordSaveService;
    private final PlayRecordFindService playRecordFindService;

    public PlayRecordController(final PlayRecordSaveService playRecordSaveService,
                                final PlayRecordFindService playRecordFindService) {
        this.playRecordSaveService = playRecordSaveService;
        this.playRecordFindService = playRecordFindService;
    }

    @GetMapping("/musics/play-record")
    public PlayRecordResponse findPlayRecord(@Login final MemberSession memberSession) {
        return playRecordFindService.findPlayRecord(memberSession.id());
    }

    @GetMapping("/musics/play-record/{memberId}")
    public PlayRecordResponse findPlayRecordByMemberId(@PathVariable final long memberId) {
        return playRecordFindService.findPlayRecord(memberId);
    }

    @PostMapping("/musics/play-record")
    public ResponseEntity<Void> savePlayRecord(@Login final MemberSession memberSession,
                                               @RequestBody final PlayRecordRequest dto) {
        playRecordSaveService.savePlayRecord(memberSession.id(), dto);
        return ResponseEntity.noContent().build();
    }
}
