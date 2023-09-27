package server.service.oauth;

import jakarta.servlet.http.HttpServletResponse;

public interface KakaoLoginService {

    void kakaoLogin(final String authCode, final HttpServletResponse response);
}
