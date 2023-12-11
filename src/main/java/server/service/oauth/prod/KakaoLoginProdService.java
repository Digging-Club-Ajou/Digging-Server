package server.service.oauth.prod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import server.domain.member.vo.Gender;
import server.mapper.jwt.dto.JwtToken;
import server.mapper.member.dto.KakaoSignupRequest;
import server.mapper.member.dto.KakaoToken;
import server.service.member.DiggingLoginService;
import server.service.oauth.KakaoLoginService;

import static server.global.constant.ExceptionMessage.KAKAO_JSON_PARSING;
import static server.global.constant.ExceptionMessage.MUSIC_JSON_PARSING;
import static server.global.constant.KakaoConstant.*;
import static server.global.constant.KakaoConstant.AUTHORIZATION;
import static server.global.constant.KakaoConstant.BEARER;
import static server.global.constant.KakaoConstant.ID;
import static server.global.constant.SpotifyConstant.*;
import static server.global.constant.SpotifyConstant.NAME;

@Profile({"prod", "dev"})
@Service
public class KakaoLoginProdService implements KakaoLoginService {

    private final DiggingLoginService diggingLoginService;
    private final ObjectMapper objectMapper;

    @Value("https://kapi.kakao.com/v2/user/me")
    private String getProfileURL;

    @Value("https://kauth.kakao.com/oauth/token")
    private String getAccessTokenURL;

    public KakaoLoginProdService(final DiggingLoginService diggingLoginService, final ObjectMapper objectMapper) {
        this.diggingLoginService = diggingLoginService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public JwtToken kakaoLogin(final String authCode){
        String accessToken = getAccessToken(authCode);
        KakaoSignupRequest kakaoProfile = getUserProfile(accessToken);
        return diggingLoginService.kakaoLogin(kakaoProfile);
    }

    private synchronized String getAccessToken(final String authCode) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(GRANT_TYPE, AUTHORIZATION_CODE);
        body.add(CLIENT_ID, KAKAO_CLIENT_ID);
        body.add(REDIRECT_URI, KAKAO_REDIRECT_URI);
        body.add(CODE, authCode);


        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                getAccessTokenURL,
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

        String header = BEARER + accessToken;
        headers.add(AUTHORIZATION, header);
        headers.add(CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                getProfileURL,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JsonNode rootNode;

        try {
            rootNode = objectMapper.readTree(response.getBody());
            JsonNode itemsNode = rootNode.path(KAKAO_ACCOUNT);

            JsonNode emailNode = itemsNode.get(EMAIL);
            JsonNode idNode = rootNode.get(ID);

            KakaoSignupRequest kakaoSignupRequest;

            if (emailNode == null) {
                kakaoSignupRequest = KakaoSignupRequest.builder()
                        .kakaoId(idNode.asLong())
                        .build();
            }else{
                kakaoSignupRequest = KakaoSignupRequest.builder()
                        .kakaoId(idNode.asLong())
                        .email(emailNode.asText())
                        .build();
            }

            return kakaoSignupRequest;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(KAKAO_JSON_PARSING.message);
        }
    }
}
