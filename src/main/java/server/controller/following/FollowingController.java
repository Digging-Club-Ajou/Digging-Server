package server.controller.following;

import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingInfoDto;
import server.mapper.following.dto.Followings;
import server.service.following.FollowingService;

@RequestMapping("/api")
@RestController
public class FollowingController {

    private FollowingService followingService;

    public FollowingController(final FollowingService followingService){
        this.followingService = followingService;
    }

    @PostMapping("/following")
    public void createFollowing(@Login final MemberSession memberSession,
                            @RequestBody final FollowingDto followingDto) {
        followingService.saveFollowing(memberSession.id(),followingDto);
    }

    @DeleteMapping("/following")
    public void deleteFollowing(@RequestBody final FollowingInfoDto followingInfoDto){
        followingService.deleteFollowing(followingInfoDto);
    }

    @GetMapping("/followings")
    public Followings getFollowingList(@RequestBody final FollowingDto followingDto){
       return followingService.getFollowingList(followingDto.memberId());
    }


}
