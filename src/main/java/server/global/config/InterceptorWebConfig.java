package server.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.global.argument_resolver.LoginArgumentResolver;
import server.global.interceptor.LoginInterceptor;
import server.repository.jwt.JwtRefreshTokenRepository;
import server.repository.member.MemberRepository;
import server.service.jwt.JwtCreateTokenService;

import java.util.List;

@Configuration
public class InterceptorWebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final JwtCreateTokenService jwtCreateTokenService;

    public InterceptorWebConfig(final ObjectMapper objectMapper, final MemberRepository memberRepository,
                                final JwtRefreshTokenRepository jwtRefreshTokenRepository,
                                final JwtCreateTokenService jwtCreateTokenService) {
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
        this.jwtCreateTokenService = jwtCreateTokenService;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(
                        objectMapper, memberRepository,
                        jwtRefreshTokenRepository, jwtCreateTokenService
                ))
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/signup", "/api/kakao", "/api/artists-info","api/member/search");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}
