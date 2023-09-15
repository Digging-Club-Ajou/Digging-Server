package server.mapper.member;

import server.domain.member.persist.Member;
import server.mapper.member.dto.MemberSignupRequest;

public class MemberMapper {

    private MemberMapper() {
    }

    public static Member toEntity(final MemberSignupRequest dto) {
        return Member.builder()
                .username(dto.username())
                .loginId(dto.loginId())
                .password(dto.password())
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .gender(dto.gender())
                .build();
    }
}
