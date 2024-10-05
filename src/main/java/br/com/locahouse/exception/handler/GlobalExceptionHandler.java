package br.com.locahouse.exception.handler;

import br.com.locahouse.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream().map(error -> Objects.requireNonNull(error.getDefaultMessage())).toList();
        ResponseError error = responseError(status, errors);
        return handleExceptionInternal(e, error, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        String message = "Unexpected server error.";
        LOGGER.error(message, e);
        ResponseError error = responseError(HttpStatus.INTERNAL_SERVER_ERROR, new ArrayList<>(Collections.singletonList(message)));
        return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
        ResponseError error = responseError(e.getHttpStatusCode(), new ArrayList<>(Collections.singletonList(e.getMessage())));
        return handleExceptionInternal(e, error, headers(), e.getHttpStatusCode(), request);
    }

    private ResponseError responseError(HttpStatusCode httpStatusCode, List<String> message) {
        return new ResponseError(LocalDateTime.now(), "error", httpStatusCode.value(), message);
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
