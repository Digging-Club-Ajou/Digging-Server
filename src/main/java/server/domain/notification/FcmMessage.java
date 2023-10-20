package server.domain.notification;

public class FcmMessage {

    private boolean validate_only;
    private Message message;

    public FcmMessage(final boolean validate_only, final Message message) {
        this.validate_only = validate_only;
        this.message = message;
    }
}
