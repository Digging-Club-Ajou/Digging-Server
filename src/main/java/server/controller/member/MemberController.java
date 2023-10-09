package server.controller.member;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.global.exception.dto.ResultResponse;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.NicknameResponse;
import server.mapper.member.dto.NicknameRequest;
import server.mapper.member.dto.NicknameValidationResponse;
import server.service.member.NicknameCreateService;
import server.service.member.NicknameValidationService;

@RequestMapping("/api")
@RestController
public class MemberController {

    private final NicknameCreateService nicknameCreateService;
    private final NicknameValidationService nicknameValidationService;

    public MemberController(final NicknameCreateService nicknameCreateService,
                            final NicknameValidationService nicknameValidationService) {
        this.nicknameCreateService = nicknameCreateService;
        this.nicknameValidationService = nicknameValidationService;
    }

    @GetMapping("/nickname")
    public NicknameResponse getNickname(@Login final MemberSession memberSession) {
        return MemberMapper.toNicknameResponse(memberSession);
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
