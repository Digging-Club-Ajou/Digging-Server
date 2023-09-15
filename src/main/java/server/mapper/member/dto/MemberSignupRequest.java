package server.mapper.member.dto;

import lombok.Builder;
import server.domain.member.vo.Gender;

@Builder
public record MemberSignupRequest(
        String username,
        String loginId,
        String password,
        String passwordCheck,
        String phoneNumber,
        String email,
        Gender gender
) {
}
