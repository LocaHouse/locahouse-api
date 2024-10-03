package br.com.locahouse.exception.handler;

import br.com.locahouse.exception.*;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(HttpStatus statusCode, String message) {
        return new ResponseError(LocalDateTime.now(), "error", statusCode.value(), message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        String message = "Unexpected server error.";
        LOGGER.error(message, e);
        ResponseError error = responseError(HttpStatus.INTERNAL_SERVER_ERROR, message);
        return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
        ResponseError error = responseError(e.getHttpStatus(), e.getMessage());
        return handleExceptionInternal(e, error, headers(), e.getHttpStatus(), request);
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidExceptio(BusinessException e, WebRequest request) {
        ResponseError error = responseError(e.getHttpStatus(), e.getMessage());
        return handleExceptionInternal(e, error, headers(), e.getHttpStatus(), request);
    }*/

    @ExceptionHandler(UniqueConstraintVioladaException.class)
    public ResponseEntity<Object> handleUniqueConstraintVioladaException(UniqueConstraintVioladaException e, WebRequest request) {
        ResponseError error = responseError(e.getHttpStatus(), e.getMessage());
        return handleExceptionInternal(e, error, headers(), e.getHttpStatus(), request);
    }

    @ExceptionHandler(UsuarioMenorDeIdadeException.class)
    public ResponseEntity<Object> handleUsuarioMenorDeIdadeException(UsuarioMenorDeIdadeException e, WebRequest request) {
        ResponseError error = responseError(e.getHttpStatus(), e.getMessage());
        return handleExceptionInternal(e, error, headers(), e.getHttpStatus(), request);
    }
}
