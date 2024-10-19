package br.com.locahouse.service;

import br.com.locahouse.model.Cep;

public interface CepService {

    Cep salvar(Cep cep);

    Cep buscarPeloNumero(String numero);

    boolean verificarExistencia(String numeroCep);
}
