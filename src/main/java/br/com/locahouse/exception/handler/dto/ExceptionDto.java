package br.com.locahouse.exception.handler.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record ExceptionDto(

        String timestamp,

        String status,

        int statusCode,

        List<String> mensagens
) {

    public ExceptionDto(HttpStatus httpStatus, List<String> mensagens) {
        this(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), httpStatus.getReasonPhrase(), httpStatus.value(), mensagens);
    }
}
