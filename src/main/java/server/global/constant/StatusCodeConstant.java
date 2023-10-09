package server.global.constant;

public enum StatusCodeConstant {

    IS_OK("200"),
    BAD_REQUEST("400"),
    UNAUTHORIZED("401"),
    FORBIDDEN("403"),
    NOT_FOUND("404");

    public final String statusCode;

    StatusCodeConstant(final String statusCode) {
        this.statusCode = statusCode;
    }
}
