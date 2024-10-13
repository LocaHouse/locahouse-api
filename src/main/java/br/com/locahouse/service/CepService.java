package br.com.locahouse.service;

import br.com.locahouse.model.Cep;

public interface CepService {

    Cep salvar(Cep cep);

    boolean verificarExistencia(String numeroCep);
}
