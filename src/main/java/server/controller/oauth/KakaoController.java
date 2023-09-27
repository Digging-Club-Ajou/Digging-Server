package server.controller.oauth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
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
    public void kakaoLogin(@RequestBody final AuthCodeRequest dto,
                           final HttpServletResponse response) {
        kakaoLoginService.kakaoLogin(dto.authCode(), response);
    }
}
