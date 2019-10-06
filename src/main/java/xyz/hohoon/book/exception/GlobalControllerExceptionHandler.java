package xyz.hohoon.book.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.ClientException.class)
    public String handleApiClientException(HttpServletRequest request, ApiException.ClientException ex) {
        log.error("request : " + request.getRequestURI());
        log.error(ex.getMessage(), ex);

        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ApiException.ServerException.class)
    public String handleApiServerException(HttpServletRequest request, ApiException.ServerException ex) {
        log.error("request : " + request.getRequestURI());
        log.error(ex.getMessage(), ex);

        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.NotAuthorized.class)
    public String handleUserDuplicatedException(HttpServletRequest request, UserException.NotAuthorized ex) {
        log.error("request : " + request.getRequestURI());
        log.error(ex.getMessage(), ex);

        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.Duplicated.class)
    public String handleUserDuplicatedException(HttpServletRequest request, UserException.Duplicated ex) {
        log.error("request : " + request.getRequestURI());
        log.error(ex.getMessage(), ex);

        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserException.Unknown.class)
    public String handleUserUnknownException(HttpServletRequest request, UserException.Unknown ex) {
        log.error("request : " + request.getRequestURI());
        log.error(ex.getMessage(), ex);

        return ex.getMessage();
    }
}
