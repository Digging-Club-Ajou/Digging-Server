package server.service.jwt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.jwt.JwtRefreshToken;
import server.domain.member.vo.MemberSession;
import server.mapper.jwt.JwtRefreshTokenMapper;
import server.repository.jwt.JwtRefreshTokenRepository;

import java.util.Optional;

import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TextConstant.REFRESH_TOKEN;

@Slf4j
@Service
public class JwtFacade {

    private final SetHeaderService setHeaderService;
    private final JwtCreateTokenService jwtCreateTokenService;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;

    public JwtFacade(final SetHeaderService setHeaderService, final JwtCreateTokenService jwtCreateTokenService,
                     final JwtRefreshTokenRepository jwtRefreshTokenRepository) {
        this.setHeaderService = setHeaderService;
        this.jwtCreateTokenService = jwtCreateTokenService;
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
    }

    public String createAccessToken(final MemberSession memberSession, final long expired) {
        return jwtCreateTokenService.createAccessToken(memberSession, expired);
    }

    public String createRefreshToken(final long memberId, final long expired) {
        String refreshToken = jwtCreateTokenService.createRefreshToken(memberId, expired);
        log.info("RefreshToken 발급={}", memberId);
        log.info("RefreshToken={}", refreshToken);
        return refreshToken;
    }

    public void setHeader(final HttpServletResponse response, final String accessToken) {
        setHeaderService.setAccessTokenHeader(response, accessToken);
    }

    @Transactional
    public void saveJwtRefreshToken(final long memberId, final String refreshToken) {
        Optional<JwtRefreshToken> optionalJwtRefreshToken = jwtRefreshTokenRepository.findByMemberId(memberId);
        if (optionalJwtRefreshToken.isPresent()) {
            JwtRefreshToken jwtRefreshToken = optionalJwtRefreshToken.get();
            jwtRefreshToken.update(refreshToken);
        } else {
            JwtRefreshToken entity = JwtRefreshTokenMapper.toEntity(memberId, refreshToken);
            jwtRefreshTokenRepository.save(entity);
        }
    }

    @Transactional
    public void deleteJwtRefreshToken(final long memberId, final HttpServletResponse response) {
        Optional<JwtRefreshToken> optionalJwtRefreshToken = jwtRefreshTokenRepository.findByMemberId(memberId);
        if (optionalJwtRefreshToken.isPresent()) {
            JwtRefreshToken jwtRefreshToken = optionalJwtRefreshToken.get();
            jwtRefreshTokenRepository.delete(jwtRefreshToken);
        }

        response.setHeader(ACCESS_TOKEN.value, null);
        response.setHeader(REFRESH_TOKEN.value, null);
    }
}
