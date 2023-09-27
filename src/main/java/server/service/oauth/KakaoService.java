package server.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import server.domain.member.vo.Gender;
import server.mapper.member.dto.KakaoProfile;
import server.mapper.member.dto.KakaoSignupRequest;
import server.mapper.member.dto.KakaoToken;


@Service
public class KakaoService {

    private final DiggingLoginService diggingLoginService;

    public KakaoService(final DiggingLoginService diggingLoginService) {
        this.diggingLoginService = diggingLoginService;
    }

    @SneakyThrows
    public void kakaoLogin(final String authCode, final HttpServletResponse response){
        String accessToken = getAccessToken(authCode);
        KakaoSignupRequest kakaoProfile = getUserProfile(accessToken);
        diggingLoginService.kakaoLogin(kakaoProfile, response);
    }

    private synchronized String getAccessToken(String authCode) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "590bb77122a44a817acaabfe3a5ea8fc");
        body.add("redirect_uri", "https://diggle.com/oauth");
        body.add("code", authCode);


        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoToken kakaoToken = objectMapper.readValue(responseBody, KakaoToken.class);
        return kakaoToken.accessToken();
    }

    private KakaoSignupRequest getUserProfile(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();

        String header = "Bearer "+ accessToken;
        headers.add("Authorization", header);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = objectMapper.readValue(responseBody,KakaoProfile.class);
        Gender gender = kakaoProfile.kakao_account().gender() == "male"?Gender.MALE:Gender.FEMALE;
        KakaoSignupRequest kakaoSignupRequest = new KakaoSignupRequest(kakaoProfile.kakao_account().email(), kakaoProfile.kakao_account().phoneNumber(),gender);

        return kakaoSignupRequest;
    }

}
