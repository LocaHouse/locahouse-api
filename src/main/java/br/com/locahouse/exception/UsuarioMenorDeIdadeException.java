package br.com.locahouse.exception;

import org.springframework.http.HttpStatus;

public class UsuarioMenorDeIdadeException extends BusinessException {

    public UsuarioMenorDeIdadeException() {
        super("É necessário ser maior de 18 anos para prosseguir.", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
