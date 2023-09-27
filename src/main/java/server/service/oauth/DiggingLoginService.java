package server.service.oauth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.service.jwt.JwtFacade;
import server.service.member.KakaoSignupService;
import server.service.member.MemberFindByEmailService;

import java.util.Optional;

import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.global.constant.TimeConstant.ONE_MONTH;

@Service
public class DiggingLoginService {

    private final MemberFindByEmailService memberFindByEmailService;
    private final KakaoSignupService kakaoSignupService;
    private final JwtFacade jwtFacade;

    public DiggingLoginService(final MemberFindByEmailService memberFindByEmailService,
                               final KakaoSignupService kakaoSignupService,
                               final JwtFacade jwtFacade) {
        this.memberFindByEmailService = memberFindByEmailService;
        this.kakaoSignupService = kakaoSignupService;
        this.jwtFacade = jwtFacade;
    }

    public void kakaoLogin(final KakaoSignupRequest dto, final HttpServletResponse response) {
        Optional<Member> optionalMember = memberFindByEmailService.findByEmail(dto.email());
        Member member = optionalMember
                .orElseGet(() -> kakaoSignupService.kakaoSignup(dto));

        MemberSession memberSession = MemberMapper.toMemberSession(member);
        createToken(response, memberSession);
    }

    private void createToken(final HttpServletResponse response, final MemberSession memberSession) {
        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
        String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
        jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
        jwtFacade.setHeader(response, accessToken, refreshToken);
    }

}
