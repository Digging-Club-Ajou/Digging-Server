package server.repository.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.jwt.JwtRefreshToken;

public interface JwtRefreshTokenJpaRepository extends JpaRepository<JwtRefreshToken, Long> {
}
