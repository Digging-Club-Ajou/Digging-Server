package server.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.global.exception.dto.ResultResponse;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.NicknameResponse;
import server.mapper.member.dto.NicknameRequest;
import server.mapper.member.dto.UsernameResponse;
import server.service.member.NicknameCreateService;
import server.service.member.NicknameFindService;
import server.service.member.NicknameValidationService;

@Slf4j
@RequestMapping("/api")
@RestController
public class MemberController {

    private final NicknameFindService nicknameFindService;
    private final NicknameCreateService nicknameCreateService;
    private final NicknameValidationService nicknameValidationService;

    public MemberController(final NicknameFindService nicknameFindService,
                            final NicknameCreateService nicknameCreateService,
                            final NicknameValidationService nicknameValidationService) {
        this.nicknameFindService = nicknameFindService;
        this.nicknameCreateService = nicknameCreateService;
        this.nicknameValidationService = nicknameValidationService;
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
}
