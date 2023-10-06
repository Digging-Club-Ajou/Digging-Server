package server.controller.album;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.album.AlbumMapper;
import server.mapper.album.dto.AlbumValidateResponse;
import server.mapper.album.dto.AlbumImageUrlResponse;
import server.service.album.AlbumCreateService;
import server.service.album.AlbumReadService;
import server.service.album.AlbumValidationService;

@RequestMapping("/api")
@RestController
public class AlbumController {

    private final AlbumCreateService albumCreateService;
    private final AlbumReadService albumReadService;
    private final AlbumValidationService albumValidationService;

    public AlbumController(final AlbumCreateService albumCreateService,
                           final AlbumReadService albumReadService,
                           final AlbumValidationService albumValidationService) {
        this.albumCreateService = albumCreateService;
        this.albumReadService = albumReadService;
        this.albumValidationService = albumValidationService;
    }

    @GetMapping("/albums-validation")
    public AlbumValidateResponse validationExist(@Login final MemberSession memberSession) {
        boolean alreadyExist = albumValidationService.validateAlreadyExist(memberSession.id());
        return AlbumMapper.toAlbumValidateResponse(alreadyExist);
    }

    @GetMapping("/albums-image")
    public AlbumImageUrlResponse getAlbumImage(@Login final MemberSession memberSession) {
        String imageUrl = albumReadService.getAlbumImageUrl(memberSession.id());
        return AlbumMapper.toAlbumImageUrlResponse(imageUrl);
    }

    @PostMapping("/albums")
    public void createAlbum(@Login final MemberSession memberSession,
                            @RequestPart final String albumName,
                            @RequestPart final MultipartFile albumImage) {
        albumCreateService.createAlbum(memberSession.id(), albumName, albumImage);
    }
}
