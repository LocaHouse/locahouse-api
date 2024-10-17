package br.com.locahouse.exception.handler;

import br.com.locahouse.dto.error.ErroDto;
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        List<String> mensagens = e.getBindingResult().getFieldErrors().stream().map(error -> Objects.requireNonNull(error.getDefaultMessage())).toList();
        return handleExceptionInternal(e, new ErroDto(HttpStatus.valueOf(statusCode.value()), mensagens), headers, statusCode, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBusinessException(BadCredentialsException e, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return handleExceptionInternal(e, new ErroDto(status, List.of(e.getMessage() + ".")), headers(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
        return handleExceptionInternal(e, new ErroDto(e.getHttpStatus(), List.of(e.getMessage())), headers(), e.getHttpStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        String mensagem = "Erro interno do servidor.";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        LOGGER.error(mensagem, e);
        return handleExceptionInternal(e, new ErroDto(status, List.of(mensagem)), headers(), status, request);
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
