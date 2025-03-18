package br.com.locahouse.exception;

import org.springframework.http.HttpStatus;

public final class UniqueConstraintVioladaException extends BusinessException {

    public UniqueConstraintVioladaException(String campo) {
        super(campo + " já cadastrado.", HttpStatus.CONFLICT);
    }
}
