package br.com.locahouse.service.integration.viacep;

import br.com.locahouse.model.Cep;

import java.io.IOException;

public interface ViaCepService {

    Cep consultar(String numeroCep) throws IOException;
}
