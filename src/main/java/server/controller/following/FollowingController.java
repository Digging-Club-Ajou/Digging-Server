package server.controller.following;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingInfoDto;
import server.mapper.following.dto.FollowingValidationResponse;
import server.mapper.following.dto.Followings;
import server.service.following.FollowingService;

@RequestMapping("/api")
@RestController
public class FollowingController {

    private final FollowingService followingService;

    public FollowingController(final FollowingService followingService){
        this.followingService = followingService;
    }

    @PostMapping("/following")
    public ResponseEntity<Void> createFollowing(@Login final MemberSession memberSession,
                                          @RequestBody final FollowingDto followingDto) {
        followingService.saveFollowing(memberSession.id(), followingDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/following")
    public ResponseEntity<Void> deleteFollowing(@RequestBody final FollowingInfoDto followingInfoDto){
        followingService.deleteFollowing(followingInfoDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/followings")
    public Followings getMemberFollowings(@Login final MemberSession memberSession){
        return followingService.getFollowingList(memberSession.id());
    }

    @GetMapping("/followings/{memberId}")
    public Followings getFollowings(@PathVariable final long memberId){
       return followingService.getFollowingList(memberId);
    }

    @GetMapping("/following-validation")
    public FollowingValidationResponse getFollowingValidation(@Login MemberSession memberSession,
                                                    @RequestParam final long memberId){
        boolean isFollowing = followingService.getFollowing(memberSession, memberId);
        return new FollowingValidationResponse(isFollowing);
    }
}
