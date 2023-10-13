package server.global.constant;

import org.springframework.beans.factory.annotation.Value;

public enum KakaoConstant {

    KAKAO_CONSTANT(){

    };


    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String BEARER = "Bearer ";
    public static final String GRANT_TYPE = "grant_type";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String CLIENT_ID = "client_id";
    public static final String KAKAO_CLIENT_ID = "590bb77122a44a817acaabfe3a5ea8fc";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String KAKAO_REDIRECT_URI = "https://diggle.com/oauth";
    public static final String CODE = "code";
}
