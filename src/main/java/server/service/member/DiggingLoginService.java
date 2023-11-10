package server.service.member;

import org.springframework.stereotype.Service;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.jwt.dto.JwtToken;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.service.jwt.JwtFacade;
import server.service.oauth.KakaoSignupService;

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

    public JwtToken kakaoLogin(final KakaoSignupRequest dto) {
        Optional<Member> optionalMember = memberFindByEmailService.findByEmail(dto.email());
        Member member;
        Boolean isNew;
        if (optionalMember.isEmpty()) {
            member = kakaoSignupService.kakaoSignup(dto);
            isNew = true;
        } else {
            member = optionalMember.get();
            isNew = false;
        }

        MemberSession memberSession = MemberMapper.toMemberSession(member);
        return createToken(memberSession, isNew);
    }

    private JwtToken createToken(final MemberSession memberSession, final Boolean isNew) {
        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
        String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
        jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
        return new JwtToken(accessToken, refreshToken, isNew);
    }
}
