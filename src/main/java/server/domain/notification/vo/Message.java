package server.domain.notification.vo;

public class Message {

    private Notification notification;
    private String token;

    public Message(final Notification notification, final String token) {
        this.notification = notification;
        this.token = token;
    }
}
