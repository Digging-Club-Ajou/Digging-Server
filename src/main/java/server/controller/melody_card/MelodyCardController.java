package server.controller.melody_card;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.mapper.melody_card.dto.MelodyCardResponses;
import server.service.melody_card.MelodyCardCreateService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class MelodyCardController {
    private final MelodyCardCreateService melodyCardCreateService;

    public MelodyCardController(final MelodyCardCreateService melodyCardCreateService) {
        this.melodyCardCreateService = melodyCardCreateService;
    }

    @GetMapping("/melody-cards/{melodyCardId}")
    public MelodyCardResponse getMelodyCard(@PathVariable final long melodyCardId) {
        return melodyCardCreateService.getMelodyCard(melodyCardId);
    }

    @GetMapping("/melody-cards/members/{memberId}")
    public MelodyCardResponses getMelodyCards(@PathVariable final long memberId) {
        List<MelodyCardResponse> melodyCards = melodyCardCreateService.getMelodyCards(memberId);
        return new MelodyCardResponses(melodyCards);
    }

    @GetMapping("/melody-cards")
    public MelodyCardResponses getMelodyCardsByLogin(@Login final MemberSession memberSession) {
        List<MelodyCardResponse> melodyCards = melodyCardCreateService.getMelodyCards(memberSession.id());
        return new MelodyCardResponses(melodyCards);
    }

    @PostMapping("/melody-cards")
    public void createMelodyCard(
            @Login final MemberSession memberSession,
            @RequestPart final MelodyCardRequest melodyCardRequest,
            @RequestPart final MultipartFile melodyImage){
        melodyCardCreateService.createMelodyCard(memberSession.id(), melodyCardRequest, melodyImage);
    }
}
