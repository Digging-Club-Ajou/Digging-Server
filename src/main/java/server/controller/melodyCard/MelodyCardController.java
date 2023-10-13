package server.controller.melodyCard;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.melodyCard.MelodyCardMapper;
import server.mapper.melodyCard.dto.MelodyCardImageUrlResponse;
import server.mapper.melodyCard.dto.MelodyCardRequest;
import server.service.melodyCard.MelodyCardCreateService;

@RequestMapping("/api")
@RestController
public class MelodyCardController {
    MelodyCardCreateService melodyCardService;


    public MelodyCardController(MelodyCardCreateService melodyCardService) {
        this.melodyCardService = melodyCardService;
    }


    @GetMapping("/melodyCard-image")
    public MelodyCardImageUrlResponse getMelodyCardImage(@Login final MemberSession memberSession) {
        String imageUrl = melodyCardService.getMelodyCardImageUrl(memberSession.id());
        return MelodyCardMapper.toMelodyCardImageUrlResponse(imageUrl);
    }

    @PostMapping("/melodyCard")
    public void createMelodyCard(
            @Login final MemberSession memberSession,
            @RequestPart final MelodyCardRequest melodyCardRequest,
            @RequestPart final MultipartFile melodyImage){

        melodyCardService.createMelodyCard(memberSession,melodyCardRequest, melodyImage);

    }

}
