package server.mapper.member.dto;

public record KakaoSignupRequest(
        String email,
        String phoneNumber
) {
}
