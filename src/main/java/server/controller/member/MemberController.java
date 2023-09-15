package server.controller.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.mapper.member.dto.MemberSignupRequest;
import server.service.member.MemberSignupService;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberSignupService memberSignupService;

    public MemberController(final MemberSignupService memberSignupService) {
        this.memberSignupService = memberSignupService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody final MemberSignupRequest dto) {
        memberSignupService.signup(dto);
    }
}
