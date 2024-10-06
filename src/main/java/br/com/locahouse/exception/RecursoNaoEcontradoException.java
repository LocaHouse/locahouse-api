package br.com.locahouse.exception;

import org.springframework.http.HttpStatus;

public class RecursoNaoEcontradoException extends BusinessException {

    public RecursoNaoEcontradoException(String recurso) {
        super("Recurso não encontrado: " + recurso + ".", HttpStatus.NOT_FOUND);
    }
}
