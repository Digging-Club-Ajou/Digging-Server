package server.service.member;

import org.springframework.stereotype.Service;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.jwt.dto.JwtToken;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.service.jwt.JwtFacade;
import server.service.oauth.KakaoSignupService;
import server.service.push_alarm.PushAlarmService;

import java.util.Optional;

import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.global.constant.TimeConstant.ONE_MONTH;

@Service
public class DiggingLoginService {

    private final MemberFindByEmailService memberFindByEmailService;
    private final MemberFindByKakaoIdService memberFindByKakaoIdService;
    private final KakaoSignupService kakaoSignupService;
    private final PushAlarmService pushAlarmService;
    private final JwtFacade jwtFacade;

    public DiggingLoginService(final MemberFindByEmailService memberFindByEmailService,
                               MemberFindByKakaoIdService memberFindByKakaoIdService,
                               final KakaoSignupService kakaoSignupService,
                               final PushAlarmService pushAlarmService, final JwtFacade jwtFacade) {
        this.memberFindByEmailService = memberFindByEmailService;
        this.memberFindByKakaoIdService = memberFindByKakaoIdService;
        this.kakaoSignupService = kakaoSignupService;
        this.pushAlarmService = pushAlarmService;
        this.jwtFacade = jwtFacade;
    }

    public JwtToken kakaoLogin(final KakaoSignupRequest dto) {
        boolean isNew;
        Member member;

        if (dto.email() == null) {
            Optional<Member> byKakaoId = memberFindByKakaoIdService.findByKakaoId(dto.kakaoId());

            if (byKakaoId.isEmpty()) {
                member = kakaoSignupService.kakaoSignup(dto);
                pushAlarmService.savePushAlarm(member.getId());
                isNew = true;
            } else {
                member = byKakaoId.get();
                isNew = false;
            }

            MemberSession memberSession = MemberMapper.toMemberSession(member);
            return createToken(memberSession, isNew);
        }

        // todo kakaoId 만으로 처리하도록 수정 필요, 기존 회원 로그인 유지를 위해 사용
        Optional<Member> optionalMember = memberFindByEmailService.findByEmail(dto.email());

        if (optionalMember.isEmpty()) {
            member = kakaoSignupService.kakaoSignup(dto);
            pushAlarmService.savePushAlarm(member.getId());
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
