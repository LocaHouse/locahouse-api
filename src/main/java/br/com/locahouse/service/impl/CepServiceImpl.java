package br.com.locahouse.service.impl;

import br.com.locahouse.model.Cep;
import br.com.locahouse.repository.CepRepository;
import br.com.locahouse.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;


public class CepServiceImpl implements CepService {

    private final CepRepository repository;

    @Autowired
    public CepServiceImpl(CepRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cep salvar(Cep cep) {
        return this.repository.save(cep);
    }

    @Override
    public boolean verificarExistencia(String numero) {
        return this.repository.findByNumero(numero).isPresent();
    }
}
