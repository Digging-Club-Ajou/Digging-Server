package server.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.global.exception.dto.ResultResponse;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.*;
import server.service.member.*;

@Slf4j
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberFindService memberFindService;
    private final NicknameFindService nicknameFindService;
    private final NicknameCreateService nicknameCreateService;
    private final NicknameValidationService nicknameValidationService;
    
    private final MemberInfoCreateService memberInfoCreateService;

    public MemberController(final MemberFindService memberFindService,
                            final NicknameFindService nicknameFindService,
                            final NicknameCreateService nicknameCreateService,
                            final NicknameValidationService nicknameValidationService,
                            final MemberInfoCreateService memberInfoCreateService) {
        this.memberFindService = memberFindService;
        this.nicknameFindService = nicknameFindService;
        this.nicknameCreateService = nicknameCreateService;
        this.nicknameValidationService = nicknameValidationService;
        this.memberInfoCreateService = memberInfoCreateService;
    }

    @GetMapping("/members")
    public MemberResponse getMember(@Login final MemberSession memberSession) {
        return memberFindService.getMember(memberSession);
    }

    @GetMapping("/nickname")
    public NicknameResponse getNickname(@Login final MemberSession memberSession) {
        String nickname = nicknameFindService.findNickname(memberSession.id());
        return MemberMapper.toNicknameResponse(nickname);
    }

    @GetMapping("/username")
    public UsernameResponse getUsername(@Login final MemberSession memberSession) {
        return MemberMapper.toUsernameResponse(memberSession.username());
    }

    @PostMapping("/nickname-validation")
    public ResultResponse validateExist(@RequestBody final NicknameRequest dto) {
        return nicknameValidationService.validateNickname(dto);
    }

    @PostMapping("/nickname")
    public void createNickname(@Login final MemberSession memberSession,
                               @RequestBody final NicknameRequest dto) {
        nicknameCreateService.createNickname(memberSession.id(), dto);
    }

    @PostMapping("/memberInfo")
    public void createMemberInfo(@Login final MemberSession memberSession,
                               @RequestBody final UserInfoRequest dto) {
        memberInfoCreateService.createMemberInfo(memberSession.id(), dto);
    }
}
