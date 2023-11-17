package server.controller.album;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.global.exception.dto.ResultResponse;
import server.mapper.album.AlbumMapper;
import server.mapper.album.dto.*;
import server.service.album.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class AlbumController {

    private final AlbumCreateService albumCreateService;
    private final AlbumImageReadService albumImageReadService;
    private final AlbumValidationService albumValidationService;
    private final AlbumFindService albumFindService;
    private final FollowingAlbumFindService followingAlbumFindService;
    private final RecommendationAlbumFindService recommendationAlbumFindService;
    private final GenreAlbumFindService genreAlbumFindService;


    public AlbumController(final AlbumCreateService albumCreateService,
                           final AlbumImageReadService albumImageReadService,
                           final AlbumValidationService albumValidationService,
                           final AlbumFindService albumFindService,
                           final FollowingAlbumFindService followingAlbumFindService,
                           final RecommendationAlbumFindService recommendationAlbumFindService,
                           final GenreAlbumFindService genreAlbumFindService) {
        this.albumCreateService = albumCreateService;
        this.albumImageReadService = albumImageReadService;
        this.albumValidationService = albumValidationService;
        this.albumFindService = albumFindService;
        this.followingAlbumFindService = followingAlbumFindService;
        this.recommendationAlbumFindService = recommendationAlbumFindService;
        this.genreAlbumFindService = genreAlbumFindService;
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

    @GetMapping("/albums")
    public AlbumResponse getLoginMemberAlbumResponse(@Login final MemberSession memberSession) {
        return albumFindService.getLoginMemberAlbumResponse(memberSession.id());
    }

    @GetMapping("/albums/following")
    public AlbumResponses getFollowedAlbumResponses(@Login final MemberSession memberSession) {
        List<AlbumResponse> albumResponses = followingAlbumFindService.findAll(memberSession.id());
        return new AlbumResponses(albumResponses);
    }

    // todo ML 로직 확정되면 변경 (임시 코드)
    @GetMapping("/albums/recommendation-ai")
    public AlbumResponses getRecommendationAlbumResponses() {
        List<AlbumResponse> albumResponses = recommendationAlbumFindService.findAll();
        return new AlbumResponses(albumResponses);
    }

    // todo 추천 장르 반환하기
    @GetMapping("/albums/recommendation-genres")
    public AlbumResponses getGenresAlbumResponses() {
        List<AlbumResponse> albumResponses = genreAlbumFindService.findAll();
        return new AlbumResponses(albumResponses);
    }

    // todo 추천 장르 반환하기
    @GetMapping("/albums/dummy")
    public AlbumResponses getDummyAlbums() {
        List<AlbumResponse> albumResponses = genreAlbumFindService.findAll();
        return new AlbumResponses(albumResponses);
    }

    @PostMapping("/albums")
    public ResponseEntity<Void> createAlbum(@Login final MemberSession memberSession,
                                            @RequestPart final AlbumNameRequest albumNameRequest,
                                            @RequestPart final MultipartFile albumImage) {
        albumCreateService.createAlbum(memberSession, albumNameRequest, albumImage);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/albums/update")
    public ResponseEntity<Void> updateAlbum(@Login final MemberSession memberSession,
                            @RequestPart final AlbumNameRequest albumNameRequest,
                            @RequestPart final MultipartFile albumImage){
        albumCreateService.updateAlbum(memberSession, albumNameRequest, albumImage);
        return ResponseEntity.noContent().build();
    }
}
