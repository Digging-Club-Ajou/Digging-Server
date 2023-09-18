package server.service.oauth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.service.jwt.JwtCreateTokenService;
import server.service.member.KakaoSignupService;
import server.service.member.MemberFindByEmailService;

import java.util.Optional;

import static server.global.constant.LoginConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;

@Service
public class KakaoLoginService {

    private final MemberFindByEmailService memberFindByEmailService;
    private final KakaoSignupService kakaoSignupService;
    private final JwtCreateTokenService jwtCreateTokenService;

    public KakaoLoginService(final MemberFindByEmailService memberFindByEmailService,
                             final KakaoSignupService kakaoSignupService,
                             final JwtCreateTokenService jwtCreateTokenService) {
        this.memberFindByEmailService = memberFindByEmailService;
        this.kakaoSignupService = kakaoSignupService;
        this.jwtCreateTokenService = jwtCreateTokenService;
    }

    public void kakaoLogin(final KakaoSignupRequest dto, final HttpServletResponse response) {
        Optional<Member> optionalMember = memberFindByEmailService.findByEmail(dto.email());
        Member member = optionalMember
                .orElseGet(() -> kakaoSignupService.kakaoSignup(dto));

        MemberSession memberSession = MemberMapper.toMemberSession(member);
        String accessToken = jwtCreateTokenService.createAccessToken(memberSession, ONE_HOUR.value);
        response.setHeader(ACCESS_TOKEN.value, accessToken);
    }
}
