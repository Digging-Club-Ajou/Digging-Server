package server.global.constant;

public enum NotificationConstant {

    NotificationConstant() {
    };

    public static final String NOTIFICATION_URL =
            "https://fcm.googleapis.com/v1/projects/digging-notification/messages:send";

    public static final String NOTIFICATION_SCOPE = "https://www.googleapis.com/auth/cloud-platform";

    public static final String FIREBASE_CONFIG_PATH = "firebase/firebase_service_key.json";
}
