package server.controller.member;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.member.dto.MemberSignupRequest;
import server.mapper.member.dto.NicknameRequest;
import server.service.member.MemberSignupService;
import server.service.member.NicknameCreateService;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberSignupService memberSignupService;
    private final NicknameCreateService nicknameCreateService;

    public MemberController(final MemberSignupService memberSignupService,
                            final NicknameCreateService nicknameCreateService) {
        this.memberSignupService = memberSignupService;
        this.nicknameCreateService = nicknameCreateService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody final MemberSignupRequest dto) {
        memberSignupService.signup(dto);
    }

    @PostMapping("/nickname")
    public void createNickname(@Login final MemberSession memberSession,
                               @RequestBody @Valid final NicknameRequest dto) {
        nicknameCreateService.createNickname(memberSession.id(), dto);
    }
}
