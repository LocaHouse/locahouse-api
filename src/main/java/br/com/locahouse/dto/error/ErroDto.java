package br.com.locahouse.dto.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record ErroDto(

        String timestamp,

        String status,

        int statusCode,

        List<String> mensagens
) {

    public ErroDto(HttpStatus httpStatus, List<String> mensagens) {
        this(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), httpStatus.getReasonPhrase(), httpStatus.value(), mensagens);
    }
}
