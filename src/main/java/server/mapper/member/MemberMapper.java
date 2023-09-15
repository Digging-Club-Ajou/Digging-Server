package server.mapper.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import server.domain.member.persist.Member;
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
}
