package server.mapper.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import server.domain.member.persist.Member;
import server.domain.member.vo.LoginType;
import server.domain.member.vo.MemberSession;
import server.mapper.member.dto.KakaoSignupRequest;
import server.mapper.member.dto.MemberResponse;
import server.mapper.member.dto.MemberSignupRequest;

public class MemberMapper {

    private MemberMapper() {
    }

    public static Member toEntity(final MemberSignupRequest dto, final PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(dto.password());

        return Member.builder()
                .username(dto.username())
                .loginId(dto.loginId())
                .password(encodedPassword)
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .gender(dto.gender())
                .build();
    }

    public static Member toEntity(final KakaoSignupRequest dto) {
        return Member.builder()
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .loginType(LoginType.KAKAO)
                .build();
    }

    public static MemberSession toMemberSession(final Member member) {
        return MemberSession.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();
    }

    public static MemberResponse toMemberResponse(final Member member) {
        return new MemberResponse(member.getNickname());
    }


}
