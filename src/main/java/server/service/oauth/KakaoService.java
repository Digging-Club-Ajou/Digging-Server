package server.service.oauth;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import server.domain.kakao.KakaoApiClient;
import server.domain.member.persist.Member;
import server.mapper.member.dto.KakaoMemberResponse;
import server.mapper.member.dto.KakaoToken;

@Service
public class KakaoService {

    private KakaoApiClient kakaoApiClient;

    public Member getToken(String authCode){
        KakaoToken kakaoToken = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
        KakaoMemberResponse kakaoMemberResponse = kakaoApiClient.fetchMember(
                kakaoToken.tokenType() + " " + kakaoToken.accessToken()
        );

        return kakaoMemberResponse.extract();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "590bb77122a44a817acaabfe3a5ea8fc");
        params.add("redirect_uri", "https://diggle.com/oauth");
        params.add("code", authCode);
//        params.add("client_secret", kakaoOauthProperties.clientSecret());
        return params;
    }
}
