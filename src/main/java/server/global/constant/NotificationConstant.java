package server.global.constant;

public enum NotificationConstant {

    NotificationConstant() {
    };

    public static final String NOTIFICATION_URL =
            "https://fcm.googleapis.com/v1/projects/digging-notification/messages:send";

    public static final String NOTIFICATION_SCOPE = "https://www.googleapis.com/auth/cloud-platform";

    public static final String FIREBASE_CONFIG_PATH = "firebase/firebase_service_key.json";
    public static final String MEDIA_TYPE_JSON_UTF_8 = "application/json; charset=utf-8";
    public static final String CONTENT_TYPE_JSON_UTF_8 = "application/json; UTF-8";
    public static final String BEARER = "Bearer ";
    public static final String MINUTES_UNIT = "분 전";
    public static final String MELODY_CARD_LIKES_NOTIFICATION = "님이 회원님의 멜로디 카드를 좋아합니다.";
    public static final String FOLLOWING_NOTIFICATION = "님이 회원님을 팔로우하기 시작했습니다.";
}
