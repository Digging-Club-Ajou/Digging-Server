package server.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.MethodNotAllowed;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import server.global.constant.ExceptionMessage;
import server.global.constant.StatusCodeConstant;
import server.global.exception.dto.ExceptionResponse;
import server.global.exception.dto.MethodArgumentExceptionResponse;

import java.util.concurrent.ConcurrentHashMap;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.StatusCodeConstant.*;
import static server.global.constant.StatusCodeConstant.METHOD_NOT_ALLOWED;

@RestControllerAdvice
public class ApiRestControllerAdvice {

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MethodArgumentExceptionResponse methodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        MethodArgumentExceptionResponse exceptionResponse = new MethodArgumentExceptionResponse(
                        BAD_REQUEST.statusCode,
                        new ConcurrentHashMap<>()
                );

        e.getFieldErrors().forEach(exceptionResponse::addValidation);
        return exceptionResponse;
    }

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponse handleException(
            final MethodArgumentTypeMismatchException e
    ) {
        return new ExceptionResponse(BAD_REQUEST.statusCode, METHOD_ARGUMENT_TYPE_MISMATCH.message);
    }

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServletRequestBindingException.class)
    public ExceptionResponse handleException(final ServletRequestBindingException e) {
        return new ExceptionResponse(BAD_REQUEST.statusCode, SERVLET_REQUEST_BINDING.message);
    }

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse handleException(final BadRequestException e) {
        return new ExceptionResponse(e.getStatusCode(), e.getMessage());
    }

    // 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ExceptionResponse handleException(final UnAuthorizedException e) {
        return new ExceptionResponse(e.getStatusCode(), e.getMessage());
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleException(final NotFoundException e) {
        return new ExceptionResponse(e.getStatusCode(), e.getMessage());
    }

    // 405
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodNotAllowed.class)
    public ExceptionResponse handleException(final MethodNotAllowed e) {
        return new ExceptionResponse(METHOD_NOT_ALLOWED.statusCode, ExceptionMessage.METHOD_NOT_ALLOWED.message);
    }
}
