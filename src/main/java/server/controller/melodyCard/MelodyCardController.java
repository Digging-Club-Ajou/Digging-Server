package server.controller.melodyCard;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.melodyCard.dto.MelodyCardRequest;
import server.mapper.melodyCard.dto.MelodyCardResponse;
import server.service.melodyCard.MelodyCardCreateService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class MelodyCardController {
    private final MelodyCardCreateService melodyCardService;

    public MelodyCardController(MelodyCardCreateService melodyCardService) {
        this.melodyCardService = melodyCardService;
    }

    @GetMapping("/melody-cards/{melodyCardId}")
    public MelodyCardResponse getMelodyCardImage(@Login final MemberSession memberSession,
                                                 @PathVariable final long melodyCardId) {
        return melodyCardService.getMelodyCardInfo(memberSession, melodyCardId);
    }

    @GetMapping("/melody-cards")
    public List<MelodyCardResponse> getMelodyCardImages(@Login final MemberSession memberSession) {
        return melodyCardService.getMelodyCardImageUrls(memberSession);
    }

    @PostMapping("/melody-cards")
    public void createMelodyCard(
            @Login final MemberSession memberSession,
            @RequestPart final MelodyCardRequest melodyCardRequest,
            @RequestPart final MultipartFile melodyImage){
        melodyCardService.createMelodyCard(memberSession.id(), melodyCardRequest, melodyImage);
    }
}
