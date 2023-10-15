package server.controller.oauth;

import org.springframework.web.bind.annotation.*;
import server.mapper.jwt.dto.JwtToken;
import server.mapper.member.dto.AuthCodeRequest;
import server.service.oauth.KakaoLoginService;

@RequestMapping("/api")
@RestController
public class KakaoController {

    private final KakaoLoginService kakaoLoginService;

    public KakaoController(final KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @PostMapping("/kakao")
    public JwtToken kakaoLogin(@RequestBody final AuthCodeRequest dto) {
        return kakaoLoginService.kakaoLogin(dto.authCode());
    }
}
