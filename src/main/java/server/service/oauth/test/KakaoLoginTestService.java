package server.service.oauth.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import server.domain.member.vo.Gender;
import server.mapper.member.dto.KakaoSignupRequest;
import server.service.member.DiggingLoginService;
import server.service.oauth.KakaoLoginService;

import static server.global.constant.TestConstant.*;

// Test Double
@Profile("test")
@Service
public class KakaoLoginTestService implements KakaoLoginService {

    private final DiggingLoginService diggingLoginService;

    public KakaoLoginTestService(final DiggingLoginService diggingLoginService) {
        this.diggingLoginService = diggingLoginService;
    }

    @Override
    public void kakaoLogin(final String authCode, final HttpServletResponse response) {
        try {
            String accessToken = getAccessToken(authCode);
            KakaoSignupRequest dto = getUserProfile(accessToken);
            diggingLoginService.kakaoLogin(dto, response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized String getAccessToken(String authCode) throws JsonProcessingException {
        return TEST_ACCESS_TOKEN.value;
    }

    private KakaoSignupRequest getUserProfile(final String accessToken) throws JsonProcessingException {
        return new KakaoSignupRequest(TEST_EMAIL.value, TEST_PHONE_NUMBER.value, Gender.UNKNOWN);
    }
}