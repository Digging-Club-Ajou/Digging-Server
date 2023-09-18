package server.controller.oauth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.mapper.member.dto.KakaoSignupRequest;

@RequestMapping("/api")
@RestController
public class KakaoController {

    @PostMapping("/kakao-login")
    public void kakaoLogin(@RequestBody final KakaoSignupRequest dto) {

    }
}
