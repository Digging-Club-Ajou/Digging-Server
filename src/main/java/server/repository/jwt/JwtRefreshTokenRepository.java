package server.repository.jwt;

import org.springframework.stereotype.Repository;
import server.domain.jwt.JwtRefreshToken;
import server.global.exception.NotFoundException;

import static server.global.constant.ExceptionMessage.*;

@Repository
public class JwtRefreshTokenRepository {

    private final JwtRefreshTokenJpaRepository jwtRefreshTokenJpaRepository;

    public JwtRefreshTokenRepository(final JwtRefreshTokenJpaRepository jwtRefreshTokenJpaRepository) {
        this.jwtRefreshTokenJpaRepository = jwtRefreshTokenJpaRepository;
    }

    public JwtRefreshToken getByMemberId(final long memberId) {
        return jwtRefreshTokenJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(REFRESH_TOKEN_NOT_FOUND_EXCEPTION.message));
    }
}
