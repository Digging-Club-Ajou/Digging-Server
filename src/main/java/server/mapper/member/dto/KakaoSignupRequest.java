package server.mapper.member.dto;

import lombok.Builder;
import server.domain.member.vo.Gender;

@Builder
public record KakaoSignupRequest(
        String email,
        String phoneNumber,
        String name,
        String ageRange
) {
}
