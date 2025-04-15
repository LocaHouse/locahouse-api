package br.com.locahouse.exception.handler;

import br.com.locahouse.exception.handler.dto.ExceptionDto;
import br.com.locahouse.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException e, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        return handleExceptionInternal(e, new ExceptionDto(HttpStatus.valueOf(statusCode.value()), e.getBindingResult().getFieldErrors().stream().map(error -> Objects.requireNonNull(error.getDefaultMessage())).toList()), headers, statusCode, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBusinessException(BadCredentialsException e, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return handleExceptionInternal(e, new ExceptionDto(status, List.of(e.getMessage() + ".")), headers(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
        return handleExceptionInternal(e, new ExceptionDto(e.getHttpStatus(), List.of(e.getMessage())), headers(), e.getHttpStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String mensagem = "Erro interno do servidor.";
        LOGGER.error("{}\n{}", mensagem, e.getMessage(), e);
        return handleExceptionInternal(e, new ExceptionDto(status, List.of(mensagem)), headers(), status, request);
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
