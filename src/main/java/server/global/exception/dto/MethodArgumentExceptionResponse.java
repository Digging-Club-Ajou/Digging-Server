package server.global.exception.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MethodArgumentExceptionResponse {

    private String statusCode;

    private Map<String, String> validation = new ConcurrentHashMap<>();

    @Builder
    private MethodArgumentExceptionResponse(final String statusCode) {
        this.statusCode = statusCode;
    }

    public void addValidation(final FieldError fieldError) {
        validation.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
