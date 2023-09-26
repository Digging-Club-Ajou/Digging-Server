package server.controller.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import server.domain.member.persist.Member;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.mapper.member.dto.MemberResponse;
import server.service.oauth.KakaoLoginService;
import server.service.oauth.KakaoService;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@RequestMapping("/api")
@RestController
public class KakaoController {

    private final KakaoLoginService kakaoLoginService;
    private final  KakaoService kakaoService;


    public KakaoController(final KakaoLoginService kakaoLoginService, final KakaoService kakaoService) {this.kakaoLoginService = kakaoLoginService;
        this.kakaoService = kakaoService;
    }

    @PostMapping("/kakao-login")
    public MemberResponse kakaoLogin(@RequestBody final KakaoSignupRequest dto,
                                     final HttpServletResponse response) {
        Member member = kakaoLoginService.kakaoLogin(dto, response);
        return MemberMapper.toMemberResponse(member);
    }


    @PostMapping("/kakao")

    public void Kakao(@RequestBody final String authCode){
        System.out.println(authCode);
        kakaoService.kakaoLogin(authCode);
    }




}
