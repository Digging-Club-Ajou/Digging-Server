package server.global.constant;

public enum ExceptionMessage {

    PASSWORD_NOT_MATCH_EXCEPTION("비밀번호가 일치하지 않습니다");

    public final String message;

    ExceptionMessage(final String message) {
        this.message = message;
    }
}
