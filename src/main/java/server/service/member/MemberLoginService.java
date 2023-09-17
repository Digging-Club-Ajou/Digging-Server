package server.service.member;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.MemberLoginRequest;
import server.service.jwt.JwtCreateTokenService;

import java.util.Optional;

import static server.global.constant.LoginConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.*;

@Service
public class MemberLoginService {

    private final MemberFindByLoginService memberFindByLoginService;
    private final JwtCreateTokenService jwtCreateTokenService;

    public MemberLoginService(final MemberFindByLoginService memberFindByLoginService,
                              final JwtCreateTokenService jwtCreateTokenService) {
        this.memberFindByLoginService = memberFindByLoginService;
        this.jwtCreateTokenService = jwtCreateTokenService;
    }

    public void login(final MemberLoginRequest dto, final HttpServletResponse response) {
        Optional<Member> optionalMember = memberFindByLoginService.findByLoginId(dto.nickname());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            MemberSession memberSession = MemberMapper.toMemberSession(member);
            String accessToken = jwtCreateTokenService.createAccessToken(memberSession, ONE_HOUR.value);
            response.setHeader(ACCESS_TOKEN.value, accessToken);
        }
    }
}
