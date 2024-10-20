package br.com.locahouse.integration;

import br.com.locahouse.model.Cep;

import java.io.IOException;

public interface ViaCepService {

    Cep consultar(String numeroCep) throws IOException;
}
