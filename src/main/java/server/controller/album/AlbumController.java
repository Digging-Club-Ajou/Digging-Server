package server.controller.album;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.global.exception.dto.ResultResponse;
import server.mapper.album.AlbumMapper;
import server.mapper.album.dto.AlbumNameRequest;
import server.mapper.album.dto.AlbumValidateResponse;
import server.mapper.album.dto.AlbumImageUrlResponse;
import server.service.album.AlbumReadService;
import server.service.album.AlbumValidationService;
import server.service.album.AlbumCreateService;

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

    @PostMapping("/albums/name-validation")
    public ResultResponse validateAlbumName(@RequestBody final AlbumNameRequest dto) {
        return dto.validateRegex();
    }

    @PostMapping("/albums-validation")
    public AlbumValidateResponse validateExist(@Login final MemberSession memberSession) {
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
                            @RequestPart @Valid final AlbumNameRequest albumNameRequest,
                            @RequestPart final MultipartFile albumImage) {
        albumCreateService.createAlbum(memberSession, albumNameRequest.albumName(), albumImage);
    }
}
