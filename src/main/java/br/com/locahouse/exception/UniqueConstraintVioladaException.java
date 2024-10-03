package br.com.locahouse.exception;

import org.springframework.http.HttpStatus;

public class UniqueConstraintVioladaException extends BusinessException {

    public UniqueConstraintVioladaException(String campo) {
        super(campo + " já cadastrado.", HttpStatus.CONFLICT);
    }
}
