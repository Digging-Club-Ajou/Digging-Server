package server.controller.oauth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.member.persist.Member;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.mapper.member.dto.MemberResponse;
import server.service.oauth.KakaoLoginService;

@RequestMapping("/api")
@RestController
public class KakaoController {

    private final KakaoLoginService kakaoLoginService;

    public KakaoController(final KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @PostMapping("/kakao-login")
    public MemberResponse kakaoLogin(@RequestBody final KakaoSignupRequest dto,
                                     final HttpServletResponse response) {
        Member member = kakaoLoginService.kakaoLogin(dto, response);
        return MemberMapper.toMemberResponse(member);
    }
}
