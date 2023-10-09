package server.global.exception.dto;

public record ResultResponse(
        String statusCode,
        String message
) {
}
