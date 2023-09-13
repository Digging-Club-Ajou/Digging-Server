package server.global.exception;

import lombok.Getter;

import static server.global.constant.StatusCodeConstant.*;

@Getter
public class BadRequestException extends RuntimeException {

    private final String statusCode = BAD_REQUEST.statusCode;
    private final String message;

    public BadRequestException(final String message) {
        this.message = message;
    }
}
