package br.com.locahouse.service;

import br.com.locahouse.model.Cep;

import java.io.IOException;

public interface ViaCepService {

    Cep consultar(String numeroCep) throws IOException;
}
