package server.controller.melodyCard;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.album.AlbumMapper;
import server.mapper.album.dto.AlbumImageUrlResponse;
import server.mapper.album.dto.AlbumNameRequest;
import server.mapper.genre.dto.GenreRequest;
import server.mapper.melodyCard.MelodyCardMapper;
import server.mapper.melodyCard.dto.MelodyCardImageUrlResponse;
import server.mapper.melodyCard.dto.MelodyCardRequest;
import server.service.melodyCard.MelodyCardService;

@RequestMapping("/api")
@RestController
public class MelodyCardController {
    MelodyCardService melodyCardService;


    public MelodyCardController(MelodyCardService melodyCardService) {
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
