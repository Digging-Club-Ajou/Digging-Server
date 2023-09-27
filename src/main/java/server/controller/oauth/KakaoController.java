package server.controller.oauth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import server.mapper.member.dto.AuthCodeRequest;
import server.service.oauth.KakaoService;

@RequestMapping("/api")
@RestController
public class KakaoController {

    private final KakaoService kakaoService;

    public KakaoController(final KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @PostMapping("/kakao")
    public void kakaoLogin(@RequestBody final AuthCodeRequest dto,
                                     final HttpServletResponse response) {
        kakaoService.kakaoLogin(dto.authCode(), response);
    }
}
