package server.controller.melody_card;

import com.google.firebase.database.annotations.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.mapper.melody_card.dto.MelodyCardResponses;
import server.service.melody_card.MelodyCardCreateService;
import server.service.melody_card.MelodyCardProdService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class MelodyCardController {
    private final MelodyCardCreateService melodyCardCreateService;
    private final MelodyCardProdService melodyCardProdService;

    public MelodyCardController(final MelodyCardCreateService melodyCardCreateService, final MelodyCardProdService melodyCardProdService) {
        this.melodyCardCreateService = melodyCardCreateService;
        this.melodyCardProdService = melodyCardProdService;
    }

    @GetMapping("/melody-cards/{melodyCardId}")
    public MelodyCardResponse getMelodyCard(@PathVariable final long melodyCardId) {
        return melodyCardCreateService.getMelodyCard(melodyCardId);
    }

    @GetMapping("/melody-cards/albums/{albumId}")
    public MelodyCardResponses getMelodyCards(@PathVariable final long albumId) {
        List<MelodyCardResponse> melodyCards = melodyCardCreateService.getMelodyCards(albumId);
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

    @DeleteMapping("/melody-cards/{melodyCardId}")
    public void deleteMelodyCard(@PathVariable final long melodyCardId){
        melodyCardProdService.deleteMelodyCardInfo(melodyCardId);
    }
}
