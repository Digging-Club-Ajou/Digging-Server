package server.mapper.member.dto;

import server.domain.member.vo.Gender;

public record KakaoSignupRequest(
        String email,
        String phoneNumber,
        Gender gender
) {
}
