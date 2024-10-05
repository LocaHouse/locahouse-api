package br.com.locahouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class BusinessException extends RuntimeException {

    HttpStatusCode httpStatusCode;

    public BusinessException(String mensagem, HttpStatusCode httpStatusCode) {
        super(mensagem);
        this.httpStatusCode = httpStatusCode;
    }
}
