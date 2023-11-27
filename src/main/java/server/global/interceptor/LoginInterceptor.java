package server.global.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import server.domain.jwt.JwtRefreshToken;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.global.exception.UnAuthorizedException;
import server.mapper.member.MemberMapper;
import server.repository.jwt.JwtRefreshTokenRepository;
import server.repository.member.MemberRepository;
import server.service.jwt.JwtCreateTokenService;

import java.util.Base64;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.JwtKey.JWT_KEY;
import static server.global.constant.TextConstant.*;
import static server.global.constant.TimeConstant.ONE_HOUR;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final byte[] decodedKey = Base64.getDecoder().decode(JWT_KEY);

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final JwtCreateTokenService jwtCreateTokenService;

    public LoginInterceptor(final ObjectMapper objectMapper,
                            final MemberRepository memberRepository,
                            final JwtRefreshTokenRepository jwtRefreshTokenRepository,
                            final JwtCreateTokenService jwtCreateTokenService) {
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
        this.jwtCreateTokenService = jwtCreateTokenService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        String accessToken = request.getHeader(ACCESS_TOKEN.value);
        MemberSession memberSession = getMemberSessionFromToken(accessToken, request, response);
        request.setAttribute(MEMBER_SESSION.value, memberSession);
        return true;
    }

    private MemberSession getMemberSessionFromToken(final String accessToken,
                                                    final HttpServletRequest request,
                                                    final HttpServletResponse response) {
        // AccessToken payload에 MemberSession 객체 정보가 저장되어 있음 -> json 파싱 필요
        try {
            Jws<Claims> claims = getClaims(accessToken);
            String memberSessionJson = claims.getBody().getSubject();
            try {
                return objectMapper.readValue(memberSessionJson, MemberSession.class);
            } catch (JsonProcessingException e) {
                throw new UnAuthorizedException(ACCESS_TOKEN_JSON_PARSING.message);
            }

        } catch (JwtException e) {
            String refreshToken = getRefreshToken(request);
            return getMemberSessionFromRefreshToken(refreshToken, response);
        }
    }

    private Jws<Claims> getClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (IllegalArgumentException e) {
            throw new UnAuthorizedException(CLAIMS_UNAUTHORIZED.message);
        }
    }

    private String getRefreshToken(final HttpServletRequest request) {
        return request.getHeader(REFRESH_TOKEN.value);
    }

    private MemberSession getMemberSessionFromRefreshToken(final String refreshToken,
                                                           final HttpServletResponse response) {
        try {
            Jws<Claims> claims = getClaims(refreshToken);
            String memberId = claims.getBody().getSubject();
            JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.getByMemberId(Long.parseLong(memberId));

            if (refreshToken.equals(jwtRefreshToken.getRefreshToken())) {
                Member member = memberRepository.getById(Long.parseLong(memberId));
                MemberSession memberSession = MemberMapper.toMemberSession(member);
                String accessToken = jwtCreateTokenService.createAccessToken(memberSession, ONE_HOUR.value);
                response.setHeader(ACCESS_TOKEN.value, accessToken);
                return memberSession;
            }

            log.info("Client RefreshToken={}", refreshToken);
            log.info("DB RefreshToken={}", jwtRefreshToken.getRefreshToken());

            throw new UnAuthorizedException(REFRESH_TOKEN_NOT_MATCH.message);
        } catch (JwtException e) {
            throw new UnAuthorizedException(REFRESH_TOKEN_NOT_VALID.message);
        }
    }
}
