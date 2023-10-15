package server.service.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import static server.global.constant.TextConstant.ACCESS_TOKEN;

@Service
public class SetHeaderService {

    public void setAccessTokenHeader(final HttpServletResponse response, final String accessToken) {
        response.setHeader(ACCESS_TOKEN.value, accessToken);
    }
}
