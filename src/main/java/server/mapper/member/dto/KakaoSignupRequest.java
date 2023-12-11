package server.mapper.member.dto;

import lombok.Builder;
import server.domain.member.vo.Gender;

@Builder
public record KakaoSignupRequest(
        Long kakaoId,
        String email,
        String phoneNumber,
        String name
) {
}
