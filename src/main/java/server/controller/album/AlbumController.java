package server.controller.album;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.global.exception.dto.ResultResponse;
import server.mapper.album.AlbumMapper;
import server.mapper.album.dto.AlbumNameRequest;
import server.mapper.album.dto.AlbumResponse;
import server.mapper.album.dto.AlbumValidateResponse;
import server.mapper.album.dto.AlbumImageUrlResponse;
import server.service.album.AlbumFindService;
import server.service.album.prod.AlbumFindProdService;
import server.service.album.AlbumImageReadService;
import server.service.album.AlbumValidationService;
import server.service.album.AlbumCreateService;

@RequestMapping("/api")
@RestController
public class AlbumController {

    private final AlbumCreateService albumCreateService;
    private final AlbumImageReadService albumImageReadService;
    private final AlbumValidationService albumValidationService;
    private final AlbumFindService albumFindService;

    public AlbumController(final AlbumCreateService albumCreateService,
                           final AlbumImageReadService albumImageReadService,
                           final AlbumValidationService albumValidationService,
                           final AlbumFindService albumFindService) {
        this.albumCreateService = albumCreateService;
        this.albumImageReadService = albumImageReadService;
        this.albumValidationService = albumValidationService;
        this.albumFindService = albumFindService;
    }

    @PostMapping("/albums/name-validation")
    public ResultResponse validateAlbumName(@RequestBody final AlbumNameRequest dto) {
        return dto.validateRegex();
    }

    @PostMapping("/albums-validation")
    public AlbumValidateResponse validateExist(@Login final MemberSession memberSession) {
        boolean alreadyExist = albumValidationService.validateExistByMemberId(memberSession.id());
        return AlbumMapper.toAlbumValidateResponse(alreadyExist);
    }

    @GetMapping("/albums-image")
    public AlbumImageUrlResponse getAlbumImage(@Login final MemberSession memberSession) {
        String imageUrl = albumImageReadService.getAlbumImageUrl(memberSession.id());
        return AlbumMapper.toAlbumImageUrlResponse(imageUrl);
    }

    @GetMapping("/albums/{albumId}")
    public AlbumResponse getAlbumResponse(@PathVariable final long albumId) {
        return albumFindService.getAlbumResponse(albumId);
    }

    @PostMapping("/albums")
    public void createAlbum(@Login final MemberSession memberSession,
                            @RequestPart final AlbumNameRequest albumNameRequest,
                            @RequestPart final MultipartFile albumImage) {
        albumCreateService.createAlbum(memberSession, albumNameRequest, albumImage);
    }
}
