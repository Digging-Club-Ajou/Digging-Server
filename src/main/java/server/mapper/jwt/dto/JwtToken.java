package server.mapper.jwt.dto;

public record JwtToken(
        String accessToken,
        String refreshToken,
        Boolean isNew
) {
}
