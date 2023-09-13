package server.global.exception.dto;

public record ExceptionResponse(
        String statusCode,
        String message
) {
}
