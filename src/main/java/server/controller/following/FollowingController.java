package server.controller.following;

import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.following.dto.FollowingDto;
import server.service.following.FollowingService;

@RequestMapping("/api")
@RestController
public class FollowingController {

    private FollowingService followingService;

    public FollowingController(final FollowingService followingService){
        this.followingService = followingService;
    }

    @PostMapping("/following")
    public void createGenre(@Login final MemberSession memberSession,
                            @RequestBody final FollowingDto followingDto) {
        followingService.saveFollowing(memberSession.id(),followingDto);
    }

    @GetMapping("/following-number")
    public void getFollowingNumber(@Login final MemberSession memberSession){

        followingService.getFollowingNumber(memberSession.id());
    }
}
