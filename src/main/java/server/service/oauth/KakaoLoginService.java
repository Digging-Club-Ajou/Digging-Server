package server.service.oauth;

import server.mapper.jwt.dto.JwtToken;

public interface KakaoLoginService {

    JwtToken kakaoLogin(final String authCode);
}
