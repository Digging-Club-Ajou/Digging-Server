package server.controller.melody_card;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.mapper.melody_card.dto.MelodyCardResponses;
import server.service.melody_card.MelodyCardCreateProdService;
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
    public MelodyCardResponse getMelodyCardImage(@Login final MemberSession memberSession,
                                                 @PathVariable final long melodyCardId) {
        return melodyCardCreateService.getMelodyCardInfo(memberSession, melodyCardId);
    }

    @GetMapping("/melody-cards")
    public MelodyCardResponses getMelodyCardImages(@Login final MemberSession memberSession) {
        List<MelodyCardResponse> melodyCards = melodyCardCreateService.getMelodyCardImageUrls(memberSession);
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
