package server.controller.album;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.service.album.AlbumCreateService;
import server.service.album.AlbumReadService;

@RequestMapping("/api")
@RestController
public class AlbumController {

    private final AlbumCreateService albumCreateService;
    private final AlbumReadService albumReadService;

    public AlbumController(final AlbumCreateService albumCreateService,
                           final AlbumReadService albumReadService) {
        this.albumCreateService = albumCreateService;
        this.albumReadService = albumReadService;
    }

    @GetMapping("/albums-image")
    public String getAlbum(@Login final MemberSession memberSession) {
        return albumReadService.getAlbumImage(memberSession.id());
    }

    @PostMapping("/albums")
    public void createAlbum(@Login final MemberSession memberSession,
                            @RequestPart final String albumName,
                            @RequestPart final MultipartFile profileImage) {
        albumCreateService.createAlbum(memberSession.id(), albumName, profileImage);
    }
}
