package server.mapper.member.dto;

import lombok.Builder;
import server.domain.member.vo.Gender;

@Builder
public record KakaoSignupRequest(
        long kakaoId,
        String email,
        String phoneNumber,
        String name
) {
}
