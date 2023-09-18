package server.mapper.jwt;

import server.domain.jwt.JwtRefreshToken;

public class JwtRefreshTokenMapper {

    private JwtRefreshTokenMapper() {
    }

    public static JwtRefreshToken toEntity(final long memberId, final String refreshToken) {
        return JwtRefreshToken.builder()
                .memberId(memberId)
                .refreshToken(refreshToken)
                .build();
    }
}
