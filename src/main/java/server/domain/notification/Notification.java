package server.domain.notification;

public class Notification {

    private String title;
    private String body;
    private String image;

    public Notification(final String title, final String body, final String image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }
}
