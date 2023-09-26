package server.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.global.interceptor.LoginInterceptor;
import server.repository.jwt.JwtRefreshTokenRepository;
import server.repository.member.MemberRepository;

@Configuration
public class InterceptorWebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;

    public InterceptorWebConfig(final ObjectMapper objectMapper, final MemberRepository memberRepository,
                                final JwtRefreshTokenRepository jwtRefreshTokenRepository) {
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(objectMapper, memberRepository, jwtRefreshTokenRepository))
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/signup", "/api/login","/api/kakao");
    }
}
