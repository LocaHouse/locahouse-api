package br.com.locahouse.service.integration.viacep;

import br.com.locahouse.model.Cep;

public interface ViaCepService {

    Cep consultar(String numeroCep);
}
