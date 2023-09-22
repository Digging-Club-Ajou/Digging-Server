package server.controller.oauth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import server.domain.member.persist.Member;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.mapper.member.dto.MemberResponse;
import server.service.oauth.KakaoLoginService;
import server.service.oauth.KakaoService;

@RequestMapping("/api")
@RestController
public class KakaoController {

    private final KakaoLoginService kakaoLoginService;
    private final  KakaoService kakaoService;


    public KakaoController(final KakaoLoginService kakaoLoginService, KakaoService kakaoService) {this.kakaoLoginService = kakaoLoginService;
        this.kakaoService = kakaoService;
    }

    @PostMapping("/kakao-login")
    public MemberResponse kakaoLogin(@RequestBody final KakaoSignupRequest dto,
                                     final HttpServletResponse response) {
        Member member = kakaoLoginService.kakaoLogin(dto, response);
        return MemberMapper.toMemberResponse(member);
    }

    @PostMapping("/kakao")
    void fetchToken(@RequestBody final String authCode){

        kakaoService.getToken(authCode);
    };
}
