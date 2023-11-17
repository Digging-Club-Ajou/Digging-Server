package server.repository.jwt;

import org.springframework.stereotype.Repository;
import server.domain.jwt.JwtRefreshToken;
import server.global.exception.NotFoundException;
import server.global.exception.UnAuthorizedException;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.*;

@Repository
public class JwtRefreshTokenRepository {

    private final JwtRefreshTokenJpaRepository jwtRefreshTokenJpaRepository;

    public JwtRefreshTokenRepository(final JwtRefreshTokenJpaRepository jwtRefreshTokenJpaRepository) {
        this.jwtRefreshTokenJpaRepository = jwtRefreshTokenJpaRepository;
    }

    public void save(final JwtRefreshToken jwtRefreshToken) {
        jwtRefreshTokenJpaRepository.save(jwtRefreshToken);
    }

    public JwtRefreshToken getByMemberId(final long memberId) {
        return jwtRefreshTokenJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UnAuthorizedException(REFRESH_TOKEN_NOT_FOUND_EXCEPTION.message));
    }

    public Optional<JwtRefreshToken> findByMemberId(final long memberId) {
        return jwtRefreshTokenJpaRepository.findByMemberId(memberId);
    }

    public void delete(final JwtRefreshToken jwtRefreshToken) {
        jwtRefreshTokenJpaRepository.delete(jwtRefreshToken);
    }
}
